package org.nightingaale.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    @Id
    @JsonIgnore
    private String correlationId;

    @JsonIgnore
    private String userId;
    private String username;
    private String info;
    private Long balance;

    private LocalDateTime createdInfoAt;
    private LocalDateTime updatedInfoAt;
}