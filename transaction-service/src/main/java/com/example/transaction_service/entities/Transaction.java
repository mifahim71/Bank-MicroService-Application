package com.example.transaction_service.entities;

import com.example.transaction_service.enums.Status;
import com.example.transaction_service.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fromAccount;
    private String toAccount;

    @NotNull(message = "Amount can't be null")
    @Min(value = 100, message = "Transaction amount must be at least 100")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type of transaction is required")
    private Type type;

    @NotNull(message = "Transaction time is required")
    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status of transaction is required")
    private Status status;
}
