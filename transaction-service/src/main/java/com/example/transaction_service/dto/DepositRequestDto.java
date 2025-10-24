package com.example.transaction_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequestDto {

    @NotBlank(message = "Deposit Account required")
    private String accountNumber;

    @Min(value = 100, message = "Deposit amount should be at least 100")
    private Double amount;
}
