package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nightingaale.userservice.dto.UserProfileDto;
import org.nightingaale.userservice.entity.UserProfileEntity;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(target = "createdInfoAt", ignore = true)
    @Mapping(target = "updatedInfoAt", ignore = true)
    UserProfileEntity toEntity(UserProfileDto dto);
    UserProfileDto toDto(UserProfileEntity entity);
}
