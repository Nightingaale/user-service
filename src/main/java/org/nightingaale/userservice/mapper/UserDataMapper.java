package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nightingaale.userservice.dto.UserDataDto;
import org.nightingaale.userservice.entity.UserDataEntity;

@Mapper(componentModel = "spring")
public interface UserDataMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDataEntity toEntity(UserDataDto userDataDto);
    UserDataDto toDto(UserDataEntity userDataEntity);
}
