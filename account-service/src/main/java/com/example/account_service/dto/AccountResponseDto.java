package com.example.account_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private String accountNumber;

    private String userId;

    private String accountType;

    private Double balance;

    private String status;

    private String createdAt;
}
