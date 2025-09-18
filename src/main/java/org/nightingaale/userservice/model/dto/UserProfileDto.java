package org.nightingaale.userservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    @JsonIgnore
    private String correlationId;

    private String userId;
    private String username;
    private String info;
    private Long balance;

    private List<String> ownedProducts;
    private List<String> purchaseHistory;
}