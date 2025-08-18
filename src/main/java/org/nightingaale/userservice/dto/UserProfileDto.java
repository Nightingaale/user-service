package org.nightingaale.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    @Id
    @JsonIgnore
    private String correlationId;

    private String userId;
    private String username;
    private String info;
    private Long balance;
}