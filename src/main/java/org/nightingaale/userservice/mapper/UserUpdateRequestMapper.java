package org.nightingaale.userservice.mapper;

import org.mapstruct.*;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.nightingaale.userservice.model.dto.UserDataDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateRequestMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "correlationId", expression = "java(correlationId)")
    KafkaUserUpdateRequestEvent toEvent(UserDataDto dataDto, String correlationId);
}
