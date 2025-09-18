package org.nightingaale.userservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.event.consumer.KafkaUserRemoveEvent;
import org.nightingaale.userservice.model.entity.UserDataEntity;
import org.nightingaale.userservice.model.entity.UserProfileEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRemoveEventMapper {

    UserDataEntity toUserDataEntity(KafkaUserRemoveEvent event);
    void updateUserDataEntityFromDto(KafkaUserRemoveEvent event, @MappingTarget UserDataEntity entity);

    UserProfileEntity toUserProfileEntity(KafkaUserRemoveEvent event);
    void updateUserProfileEntityFromDto(KafkaUserRemoveEvent event, @MappingTarget UserProfileEntity entity);
}

