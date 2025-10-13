package org.nightingaale.userservice.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private String userId;
    private String correlationId;
    private String username;
    private String password;
    private BigDecimal balance;
    private String email;
}