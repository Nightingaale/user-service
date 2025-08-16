package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.entity.UserDataEntity;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.event.UserRemoveEvent;
import org.nightingaale.userservice.mapper.UserRegistrationEventMapper;
import org.nightingaale.userservice.repository.UserDataRepository;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserRegistrationEventMapper userRegistrationEventMapper;
    private final UserDataRepository userDataRepository;

    @Transactional
    public void createProfile(UserRegistrationEvent event) {
        try {
            if (userProfileRepository.existsById(event.getUserId())) {
                log.warn("[User with id: " + event.getUserId() + " already exists]");
                return;
            }

            UserDataEntity userEntity = userRegistrationEventMapper.toUserDataEntity(event);
            userDataRepository.save(userEntity);

            UserProfileEntity userProfileEntity = userRegistrationEventMapper.toUserProfileEntity(event);
            userProfileRepository.save(userProfileEntity);

            log.info("[User with id: " + event.getUserId() + "] has been created");
        } catch (RuntimeException e) {
            log.error("[User with id: " + event.getUserId() + "] could not be created", e);
        }
    }

    @Transactional
    public void deleteProfile(UserRemoveEvent event) {
        try {
            if (!userProfileRepository.existsById(event.getUserId())) {
                log.warn("[User with ID: " + event.getUserId() + " does not exist]");
                return;
            }

            userDataRepository.deleteById(event.getUserId());
            userProfileRepository.deleteById(event.getUserId());

            log.info("[User with ID: " + event.getUserId() + "] has been deleted");
        } catch (RuntimeException e) {
            log.error("[User with ID: " + event.getUserId() + "] could not be deleted", e);
        }
    }
}