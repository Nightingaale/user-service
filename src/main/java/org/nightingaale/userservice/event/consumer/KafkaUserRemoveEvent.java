package org.nightingaale.userservice.event.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaUserRemoveEvent {
    private String correlationId;
    private String userId;
}
