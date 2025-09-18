package org.nightingaale.userservice.event.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaUserRegisteredEvent {
    private String correlationId;
    private String userId;
    private boolean userExists;
}
