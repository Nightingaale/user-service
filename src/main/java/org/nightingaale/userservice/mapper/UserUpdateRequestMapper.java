package org.nightingaale.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.nightingaale.userservice.model.dto.UserDataDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateRequestMapper {
    @Mapping(target = "correlationId", source = "correlationId")
    KafkaUserUpdateRequestEvent toEvent(UserDataDto dataDto, String correlationId);
}
