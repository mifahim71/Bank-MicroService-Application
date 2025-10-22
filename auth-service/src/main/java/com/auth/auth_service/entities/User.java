package com.auth.auth_service.entities;

import com.auth.auth_service.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Valid email Address required")
    @NotBlank(message = "Email Address required")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8, message = "Minimum 8 Characters required")
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
