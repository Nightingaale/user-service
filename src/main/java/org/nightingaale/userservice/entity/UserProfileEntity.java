package org.nightingaale.userservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileEntity {

    @JsonProperty("_id")
    private String correlationId;

    @Id
    @JsonProperty("userId")
    private String userId;

    @Size(min = 5, max = 20)
    private String username;

    @Size(max = 256)
    private String info;

    private Long balance = 0L;

    @Field(value = "ownedItems")
    private List<String> ownedProducts = new ArrayList<>();

    @Field(value = "purchaseHistory")
    private List<String> purchaseHistory = new ArrayList<>();

    @CreatedDate
    @Field(value = "createdInfoAt")
    private LocalDateTime createdInfoAt;

    @LastModifiedDate
    @Field(value = "updatedInfoAt")
    private LocalDateTime updatedInfoAt;
}