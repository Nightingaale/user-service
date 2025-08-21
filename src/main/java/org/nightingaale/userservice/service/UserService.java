package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.dto.UserProfileDto;
import org.nightingaale.userservice.entity.UserDataEntity;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegisteredEvent;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.event.UserRemoveEvent;
import org.nightingaale.userservice.event.UserRemovedEvent;
import org.nightingaale.userservice.mapper.UserProfileMapper;
import org.nightingaale.userservice.mapper.UserRegistrationEventMapper;
import org.nightingaale.userservice.repository.UserDataRepository;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, UserRemovedEvent> userRemovedTemplate;
    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;

    @Transactional
    public void createProfile(UserRegistrationEvent event) {
        try {
            if (userDataRepository.existsById(event.getUserId())) {
                log.warn("[User with ID: {} already exists]", event.getUserId());
                return;
            }

            UserDataEntity userEntity = userRegistrationEventMapper.toUserDataEntity(event);
            userDataRepository.save(userEntity);

            UserProfileEntity userProfileEntity = userRegistrationEventMapper.toUserProfileEntity(event);
            userProfileRepository.save(userProfileEntity);

            userRegisteredTemplate.send("user-registered", new UserRegisteredEvent(event.getCorrelationId(), event.getUserId(), true));
            log.info("[Send Kafka user-registered event to auth-service: {}", event.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be created", event.getUserId(), e);
        }
    }

    @Transactional
    public void deleteProfile(UserRemoveEvent event) {
        try {
            if (userDataRepository.existsById(event.getUserId())) {
                log.info("[User with ID: {} exists. Try to delete data...]", event.getUserId());
            }

            userDataRepository.deleteByUserId(event.getUserId());
            userProfileRepository.deleteByUserId(event.getUserId());

            userRemovedTemplate.send("user-removed", new UserRemovedEvent(event.getCorrelationId(), event.getUserId(), false));
            log.info("[Send Kafka user-removed event to auth-service: {}", event.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be deleted]", event.getUserId(), e);
        }
    }

    public Optional<UserProfileDto> getProfileById(String userId) {
        try {
            if (!userDataRepository.existsById(userId)) {
                log.info("[User with ID: {} does not exists]", userId);
            }

            return userProfileRepository.findById(userId)
                    .map(userProfileMapper::toDto);

        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be found]", userId, e);
            return Optional.empty();
        }
    }
}