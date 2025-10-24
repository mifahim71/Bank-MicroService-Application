package com.example.transaction_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequestDto {

    @NotBlank(message = "Withdraw Account required")
    private String accountNumber;

    @Min(value = 100, message = "Withdraw amount should be at least 100")
    private Double amount;
}
