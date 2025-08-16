package org.nightingaale.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {

    private String correlationId;
    private String userId;
    private String username;
    private String password;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}