package org.nightingaale.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRemoveEvent {
    private String correlationId;
    private String userId;
}
