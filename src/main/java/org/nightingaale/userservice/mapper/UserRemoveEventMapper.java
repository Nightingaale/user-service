package org.nightingaale.userservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.entity.UserDataEntity;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRemoveEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRemoveEventMapper {

    UserDataEntity toUserDataEntity(UserRemoveEvent event);
    void updateUserDataEntityFromDto(UserRemoveEvent event, @MappingTarget UserDataEntity entity);

    UserProfileEntity toUserProfileEntity(UserRemoveEvent event);
    void updateUserProfileEntityFromDto(UserRemoveEvent event, @MappingTarget UserProfileEntity entity);
}

