package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.model.dto.UserProfileDto;
import org.nightingaale.userservice.model.entity.UserProfileEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper {
    UserProfileDto toDto(UserProfileEntity entity);
    UserProfileEntity toEntity(UserProfileDto dto);
}
