package org.nightingaale.userservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users_data")
@AllArgsConstructor
@NoArgsConstructor
public class UserDataEntity {

    @Id
    private String userId;

    private String correlationId;

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

    private BigDecimal balance = BigDecimal.valueOf(0L);

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}