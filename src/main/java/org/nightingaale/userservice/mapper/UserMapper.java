package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nightingaale.userservice.dto.UserDto;
import org.nightingaale.userservice.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdInfoAt", ignore = true)
    @Mapping(target = "updatedInfoAt", ignore = true)

    UserEntity toEntity(UserDto dto);
    UserDto toDto(UserEntity entity);
}
