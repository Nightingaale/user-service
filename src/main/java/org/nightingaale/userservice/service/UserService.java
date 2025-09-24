package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.model.dto.UserDataDto;
import org.nightingaale.userservice.model.dto.UserProfileDto;
import org.nightingaale.userservice.model.entity.UserDataEntity;
import org.nightingaale.userservice.model.entity.UserProfileEntity;
import org.nightingaale.userservice.event.producer.KafkaUserRegisteredEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRegistrationEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRemoveEvent;
import org.nightingaale.userservice.event.producer.KafkaUserRemovedEvent;
import org.nightingaale.userservice.mapper.UserProfileMapper;
import org.nightingaale.userservice.mapper.UserRegistrationEventMapper;
import org.nightingaale.userservice.repository.UserDataRepository;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserDataRepository userDataRepository;
    private final UserRegistrationEventMapper userRegistrationEventMapper;
    private final UserProfileMapper userProfileMapper;
    private final KafkaTemplate<String, KafkaUserRemovedEvent> userRemovedTemplate;
    private final KafkaTemplate<String, KafkaUserRegisteredEvent> userRegisteredTemplate;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createProfile(KafkaUserRegistrationEvent event) {
        try {
            if (userDataRepository.existsById(event.getUserId())) {
                log.warn("[User with ID: {} already exists]", event.getUserId());
                return;
            }

            UserDataEntity userEntity = userRegistrationEventMapper.toUserDataEntity(event);
            userDataRepository.save(userEntity);

            UserProfileEntity userProfileEntity = userRegistrationEventMapper.toUserProfileEntity(event);
            userProfileRepository.save(userProfileEntity);

            userRegisteredTemplate.send("user-registered", new KafkaUserRegisteredEvent(event.getCorrelationId(), event.getUserId(), true));
            log.info("[Send Kafka user-registered event to auth-service: {}", event.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be created", event.getUserId(), e);
        }
    }

    @Transactional
    public void deleteProfile(KafkaUserRemoveEvent event) {
        try {
            if (userDataRepository.existsById(event.getUserId())) {
                log.info("[User with ID: {} exists. Try to delete data...]", event.getUserId());
            }

            userDataRepository.deleteByUserId(event.getUserId());
            userProfileRepository.deleteByUserId(event.getUserId());

            userRemovedTemplate.send("user-removed", new KafkaUserRemovedEvent(event.getCorrelationId(), event.getUserId(), false));
            log.info("[Send Kafka user-removed event to auth-service: {}", event.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be deleted]", event.getUserId(), e);
        }
    }

    public Optional<UserProfileDto> getProfileById(String userId) {
        return userProfileRepository.findById(userId)
                .map(userProfileMapper::toDto);
    }

    public void updateProfile(UserDataDto dataDto) {
        try {
            userDataRepository.findById(dataDto.getUserId()).ifPresentOrElse(user -> {
                Optional.ofNullable(dataDto.getUsername())
                        .ifPresent(user::setUsername);
                Optional.ofNullable(dataDto.getPassword())
                        .ifPresent(password -> {user.setPassword(passwordEncoder.encode(password));});
                Optional.ofNullable(dataDto.getEmail())
                        .ifPresent(user::setEmail);

                userDataRepository.save(user);

                log.info("[User's data with ID: {} successfully updated]", dataDto.getUserId());
            }, () -> {
                log.warn("[User with ID: {} doesn't exist]", dataDto.getUserId());
            });

            userProfileRepository.findById(dataDto.getUserId()).ifPresentOrElse(profile -> {
                Optional.ofNullable(dataDto.getUsername())
                        .ifPresent(profile::setUsername);

                userProfileRepository.save(profile);

                log.info("[User's profile with ID: {} successfully updated]", dataDto.getUserId());
            }, () -> {
                log.warn("[User's profile with ID: {} doesn't exist]", dataDto.getUserId());
            });

        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be updated]", dataDto.getUserId(), e);
            throw e;
        }
    }
}