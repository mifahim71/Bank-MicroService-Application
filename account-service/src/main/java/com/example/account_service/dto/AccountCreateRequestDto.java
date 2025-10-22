package com.example.account_service.dto;

import com.example.account_service.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequestDto {

    @NotNull(message = "AccountType is required")
    private AccountType accountType;

    @NotNull(message = "Initial deposit is required")
    @Min(value = 100, message = "Initial deposit must be at least 100")
    private Double initialDeposit;
}
