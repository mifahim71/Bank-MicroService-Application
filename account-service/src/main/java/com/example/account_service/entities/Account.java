package com.example.account_service.entities;

import com.example.account_service.enums.AccountType;
import com.example.account_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull(message = "Balance is required")
    private Double balance;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
}
