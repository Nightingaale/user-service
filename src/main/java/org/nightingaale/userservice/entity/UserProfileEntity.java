package org.nightingaale.userservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users_info")
@Getter
@Setter
public class UserProfileEntity {
    @Id
    @JsonProperty("_id")
    private String correlationId;

    @JsonProperty("userId")
    private String userId;

    private String username;

    @Size(max = 256)
    private String info;

    private Long balance;

    @CreatedDate
    private LocalDateTime createdInfoAt;

    @LastModifiedDate
    private LocalDateTime updatedInfoAt;
}