package org.nightingaale.userservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.event.UserRemoveEvent;
import org.nightingaale.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaUserListener {

    private final UserService userService;

    @KafkaListener(topics = "user-registration", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserRegistration")
    public void userRegistration(UserRegistrationEvent event) {
        log.info("[Received user-registration Kafka event: {}, {}]", event.getCorrelationId(), event.getUserId());
        userService.createProfile(event);
    }

    @KafkaListener(topics = "user-remove", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserRemove")
    public void userRemoval(UserRemoveEvent event) {
        log.info("[Received user-remove Kafka event: {}, {}]", event.getCorrelationId(), event.getUserId());
        userService.deleteProfile(event);
    }
}
