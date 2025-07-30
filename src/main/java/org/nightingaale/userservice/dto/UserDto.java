package org.nightingaale.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDto {
    @Id
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String userId;
    private String username;
    private String firstName;
    private String lastname;

    @JsonProperty("bio")
    private List<String> bio;

    private LocalDateTime createdInfoAt;
    private LocalDateTime updatedInfoAt;
}
