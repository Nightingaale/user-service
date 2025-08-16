package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.entity.UserDataEntity;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegistrationEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRegistrationEventMapper {

    UserDataEntity toUserDataEntity(UserRegistrationEvent event);
    void updateUserDataEntityFromDto(UserRegistrationEvent event, @MappingTarget UserDataEntity entity);

    UserProfileEntity toUserProfileEntity(UserRegistrationEvent event);
    void updateUserProfileEntityFromDto(UserRegistrationEvent event, @MappingTarget UserProfileEntity entity);
}

