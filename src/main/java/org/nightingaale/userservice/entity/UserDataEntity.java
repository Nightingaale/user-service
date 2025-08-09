package org.nightingaale.userservice.entity;

import jakarta.persistence.*;
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
    private String id;

    @Column(nullable = false, unique = true)
    @Size(max = 32)
    private String username;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    @Size(max = 32)
    private String firstName;

    @NotNull
    @Size(max = 32)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(max = 64)
    private String email;

    @NotNull
    @Size(max = 15)
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}