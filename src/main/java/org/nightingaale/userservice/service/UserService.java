package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.entity.UserDataEntity;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.event.UserRemoveEvent;
import org.nightingaale.userservice.event.UserRemovedEvent;
import org.nightingaale.userservice.mapper.UserRegistrationEventMapper;
import org.nightingaale.userservice.repository.UserDataRepository;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserRegistrationEventMapper userRegistrationEventMapper;
    private final UserDataRepository userDataRepository;
    private final KafkaTemplate<String, UserRemovedEvent> userRemovedTemplate;

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

            log.info("[User with ID: {} has been created", event.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be created", event.getUserId(), e);
        }
    }

    @Transactional
    public void deleteProfile(UserRemoveEvent event) {
        try {
            if (userDataRepository.existsById(event.getUserId())) {
                log.warn("[User with ID: {} exists. Try to delete data...]", event.getUserId());
            }

            userDataRepository.deleteByUserId(event.getUserId());
            userProfileRepository.deleteByUserId(event.getUserId());

            userRemovedTemplate.send("user-removed", new UserRemovedEvent(event.getCorrelationId(), event.getUserId(), false));
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be deleted]", event.getUserId(), e);
        }
    }

    public void getUser(UserProfileEntity profile) {
        try {
            if (!userProfileRepository.existsById(profile.getUserId())) {
                log.warn("[User with ID: {} does not exists in MongoDB]", profile.getUserId());
                return;
            }

            userProfileRepository.findById(profile.getUserId());

            log.info("[User with ID: {} has been found", profile.getUserId());
        } catch (RuntimeException e) {
            log.error("[User with ID: {} could not be found", profile.getUserId(), e);
        }
    }
}