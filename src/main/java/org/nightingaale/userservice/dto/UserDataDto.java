package org.nightingaale.userservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private String correlationId;
    private String userId;
    private String username;
    private String password;
    private String email;
}