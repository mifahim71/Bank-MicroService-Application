package com.example.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateResponseDto {

    private String accountNumber;

    private String userId;

    private Double balance;

    private String Status;
}
