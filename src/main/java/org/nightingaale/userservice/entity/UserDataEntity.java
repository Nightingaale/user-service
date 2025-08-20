package org.nightingaale.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users_data")
public class UserDataEntity {

    @Id
    private String correlationId;

    private String userId;

    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @NotNull
    @Email
    @Size(max = 60)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    private Long balance = 0L;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}