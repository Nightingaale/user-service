package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.client.AuthServiceClient;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.nightingaale.userservice.exception.DuplicateFieldException;
import org.nightingaale.userservice.filter.UserServiceFilter;
import org.nightingaale.userservice.mapper.UserUpdateRequestMapper;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserUpdateRequestMapper userUpdateRequestMapper;
    private final KafkaTemplate<String, KafkaUserRemovedEvent> userRemovedTemplate;
    private final KafkaTemplate<String, KafkaUserRegisteredEvent> userRegisteredTemplate;
    private final KafkaTemplate<String, KafkaUserUpdateRequestEvent> userUpdateTemplate;
    private final UserServiceFilter userServiceFilter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthServiceClient authServiceClient;

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

    @Transactional
    public void requestToUpdate(UserDataDto dataDto) {
        try {
            userServiceFilter.userValidation(dataDto);

            UserDataEntity user = userDataRepository.findByUserId(dataDto.getUserId())
                    .orElseThrow(() -> new RuntimeException(
                            "User not found for userId: " + dataDto.getUserId()
                    ));
            String correlationId = user.getCorrelationId();

            KafkaUserUpdateRequestEvent event = userUpdateRequestMapper.toEvent(dataDto, correlationId);

//            userUpdateTemplate.send("user-update", event);
//            log.info("[Send Kafka user-update event to auth-service: {}, {}", event.getUserId(), event.getCorrelationId());
            authServiceClient.updateUser(event);
        } catch (DuplicateFieldException e) {
            log.error("[User with ID: {} could not be updated", dataDto.getUserId(), e);
            throw e;
        }
    }

    @Transactional
    public void updateProfile(KafkaUserUpdateRequestEvent event) {
        try {
            userDataRepository.findById(event.getUserId()).ifPresentOrElse(user -> {
                Optional.ofNullable(event.getUsername())
                        .ifPresent(user::setUsername);
                Optional.ofNullable(event.getPassword())
                        .ifPresent(password -> {user.setPassword(bCryptPasswordEncoder.encode(password));});
                Optional.ofNullable(event.getEmail())
                        .ifPresent(user::setEmail);

                userDataRepository.save(user);

                log.info("[User's data with ID: {} successfully updated]", event.getUserId());
            }, () -> {
                log.warn("[User with ID: {} doesn't exist]", event.getUserId());
            });

            userProfileRepository.findById(event.getUserId()).ifPresentOrElse(profile -> {
                Optional.ofNullable(event.getUsername())
                        .ifPresent(profile::setUsername);

                userProfileRepository.save(profile);

                log.info("[User's profile with ID: {} successfully updated]", event.getUserId());
            }, () -> {
                log.warn("[User's profile with ID: {} doesn't exist]", event.getUserId());
            });

        } catch (RuntimeException e) {
            log.error("[User's data with ID: {} could not be updated]", event.getUserId(), e);
            log.error("[User's profile with ID: {} could not be updated]", event.getUserId(), e);
            throw e;
        }
    }
}