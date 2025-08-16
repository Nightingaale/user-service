package org.nightingaale.userservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users_info")
@Getter
@Setter
public class UserProfileEntity {
    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("userId")
    private String userId;

    @Size(max = 16)
    private String username;

    @Size(max = 32)
    private String firstName;

    @Size(max = 32)
    private String lastName;

    @JsonProperty("bio")
    @Size(max = 256)
    private List<String> bio = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdInfoAt;

    @LastModifiedDate
    private LocalDateTime updatedInfoAt;
}