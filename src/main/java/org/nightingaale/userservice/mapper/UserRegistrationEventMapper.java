package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.event.consumer.KafkaUserRegistrationEvent;
import org.nightingaale.userservice.model.entity.UserDataEntity;
import org.nightingaale.userservice.model.entity.UserProfileEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRegistrationEventMapper {

    UserDataEntity toUserDataEntity(KafkaUserRegistrationEvent event);
    void updateUserDataEntityFromDto(KafkaUserRegistrationEvent event, @MappingTarget UserDataEntity entity);

    UserProfileEntity toUserProfileEntity(KafkaUserRegistrationEvent event);
    void updateUserProfileEntityFromDto(KafkaUserRegistrationEvent event, @MappingTarget UserProfileEntity entity);
}

