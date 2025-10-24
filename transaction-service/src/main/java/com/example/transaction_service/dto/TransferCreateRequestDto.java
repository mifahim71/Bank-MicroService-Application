package com.example.transaction_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferCreateRequestDto {

    @NotBlank(message = "Sender Account Number required")
    private String fromAccount;

    @NotBlank(message = "receiver Account Number required")
    private String toAccount;

    @NotNull(message = "Amount can't be null")
    @Min(value = 100, message = "Transaction amount must be at least 100")
    private Double amount;
}
