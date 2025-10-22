package com.example.account_service.service;

import com.example.account_service.dto.*;
import com.example.account_service.entities.Account;
import com.example.account_service.enums.Status;
import com.example.account_service.exceptions.AccountDoesNotBelongToThisUserException;
import com.example.account_service.exceptions.AccountNotFoundException;
import com.example.account_service.exceptions.AccountWithThisUserIdAndAccountTypeAlreadExistsException;
import com.example.account_service.mapper.AccountMapper;
import com.example.account_service.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountCreateResponseDto createAccount(AccountCreateRequestDto requestDto, String userId) {

        if (accountRepository.existsByUserIdAndAccountType(userId, requestDto.getAccountType())){
            throw new AccountWithThisUserIdAndAccountTypeAlreadExistsException("Account with user Id: "+userId+" and Account Type: "+requestDto.getAccountType().toString()+" already exists");
        }

        Account account = Account.builder()
                .accountNumber("ACCT" + userId.substring(0, 4).toUpperCase() + System.currentTimeMillis() % 100)
                .accountType(requestDto.getAccountType())
                .balance(requestDto.getInitialDeposit())
                .userId(userId)
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toAccountCreateResponseDto(savedAccount);
    }

    public AccountResponseDto getAccountByAccountNumber(String accountNumber, String userId) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account number: " + accountNumber + " not found"));

        if(!account.getUserId().equals(userId)){
            throw new AccountDoesNotBelongToThisUserException("Account doesn't belong to this user");
        }

        return accountMapper.toAccountResponseDto(account);
    }


    public List<AccountResponseDto> getAllAccountOfUser(String userId) {

        List<Account> accounts = accountRepository.findByUserId(userId);

        return accounts.stream().map(accountMapper::toAccountResponseDto).toList();
    }

    public AccountStatusUpdateResponseDto updateStatus(@Valid AccountStatusUpdateRequestDto requestDto, String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account number: " + accountNumber + " not found"));

        account.setStatus(requestDto.getStatus());

        Account savedAccount = accountRepository.save(account);

        return new AccountStatusUpdateResponseDto("Account status "+savedAccount.getStatus().toString()+" successfully");
    }
}
