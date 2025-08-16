package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nightingaale.userservice.entity.UserProfileEntity;
import org.nightingaale.userservice.event.UserRegistrationEvent;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    @Mapping(source = "userId", target = "id")
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "bio", ignore = true)
    @Mapping(target = "createdInfoAt", ignore = true)
    @Mapping(target = "updatedInfoAt", ignore = true)
    UserProfileEntity fromRegistrationEvent(UserRegistrationEvent event);
}