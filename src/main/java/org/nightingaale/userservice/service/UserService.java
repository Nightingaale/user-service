package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.mapper.UserRegistrationMapper;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserRegistrationMapper userRegistrationMapper;

    @KafkaListener(topics = "user-registration", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserRegistered")
    public void createProfile(UserRegistrationEvent event) {
        try {
            if (userProfileRepository.existsById(event.getUserId())) {
                log.warn("[User with id: " + event.getUserId() + " already exists]");
                return;
            }

            UserProfileEntity userEntity = userRegistrationMapper.fromRegistrationEvent(event);
            userProfileRepository.save(userEntity);

            log.info("[User's profile with id: " + event.getUserId() + "] has been created");
        } catch (RuntimeException e) {
            log.error("[User's profile with id: " + event.getUserId() + "] could not be created", e);
        }
    }

    public void deleteProfile(String userId) {
        try {
            if (userProfileRepository.existsById(userId)) {
                log.warn("[User with id: " + userId + " does not exist]");
                throw new RuntimeException("User profile is not found");
            }

            userProfileRepository.deleteById(userId);
            log.info("[User's info with id: " + userId + "has been deleted]");
        } catch (RuntimeException e) {
            log.error("[User's info with id: " + userId + "] could not be deleted", e);
        }
    }
}