package com.example.account_service.mapper;

import com.example.account_service.dto.AccountCreateResponseDto;
import com.example.account_service.dto.AccountResponseDto;
import com.example.account_service.entities.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountMapper {

    public AccountCreateResponseDto toAccountCreateResponseDto(Account account){

        AccountCreateResponseDto accountCreateResponseDto = new AccountCreateResponseDto();

        accountCreateResponseDto.setAccountNumber(account.getAccountNumber());
        accountCreateResponseDto.setStatus(account.getStatus().toString());
        accountCreateResponseDto.setBalance(account.getBalance());
        accountCreateResponseDto.setUserId(account.getUserId());

        return accountCreateResponseDto;
    }

    public AccountResponseDto toAccountResponseDto(Account account){

        AccountResponseDto responseDto = new AccountResponseDto();

        responseDto.setAccountNumber(account.getAccountNumber());
        responseDto.setAccountType(account.getAccountType().toString());
        responseDto.setStatus(account.getStatus().toString());
        responseDto.setBalance(account.getBalance());
        responseDto.setUserId(account.getUserId());
        responseDto.setCreatedAt(account.getCreatedAt().toString());

        return responseDto;
    }
}
