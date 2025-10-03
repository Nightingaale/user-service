package org.nightingaale.userservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.event.consumer.KafkaUserRegistrationEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRemoveEvent;
import org.nightingaale.userservice.model.dto.UserDataDto;
import org.nightingaale.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaUserListener {

    private final UserService userService;

    @KafkaListener(topics = "user-registration", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserRegistration")
    public void userRegistration(KafkaUserRegistrationEvent event) {
        log.info("[Received user-registration Kafka event: {}, {}]", event.getCorrelationId(), event.getUserId());
        userService.createProfile(event);
    }

    @KafkaListener(topics = "user-remove", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserRemove")
    public void userRemoval(KafkaUserRemoveEvent event) {
        log.info("[Received user-remove Kafka event: {}, {}]", event.getCorrelationId(), event.getUserId());
        userService.deleteProfile(event);
    }

    @KafkaListener(topics = "user-updated", groupId = "user-service", containerFactory = "kafkaListenerContainerFactoryUserUpdated")
    public void updateEvent(UserDataDto dataDto) {
        log.info("Receive user-updated Kafka event: {}, {}", dataDto.getCorrelationId(), dataDto.getUserId());
        userService.updateProfile(dataDto);
    }
}
