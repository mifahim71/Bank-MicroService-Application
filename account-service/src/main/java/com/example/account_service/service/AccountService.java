package com.example.account_service.service;

import com.example.account_service.client.TransactionClient;
import com.example.account_service.dto.*;
import com.example.account_service.entities.Account;
import com.example.account_service.enums.Status;
import com.example.account_service.exceptions.AccountDoesNotBelongToThisUserException;
import com.example.account_service.exceptions.AccountNotFoundException;
import com.example.account_service.exceptions.AccountWithThisUserIdAndAccountTypeAlreadExistsException;
import com.example.account_service.mapper.AccountMapper;
import com.example.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
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

    private final TransactionClient transactionClient;

    public AccountCreateResponseDto createAccount(AccountCreateRequestDto requestDto, String userId) {

        if (accountRepository.existsByUserIdAndAccountType(userId, requestDto.getAccountType())){
            throw new AccountWithThisUserIdAndAccountTypeAlreadExistsException("Account with user Id: "+userId+" and Account Type: "+requestDto.getAccountType().toString()+" already exists");
        }

        Account account = Account.builder()
                .accountNumber("ACCT" + userId.substring(0, 4).toUpperCase() + System.currentTimeMillis() % 100)
                .accountType(requestDto.getAccountType())
                .balance(0.0)
                .userId(userId)
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        transactionClient.deposit(savedAccount.getAccountNumber(), requestDto.getInitialDeposit());

        AccountCreateResponseDto responseDto = accountMapper.toAccountCreateResponseDto(savedAccount);
        responseDto.setBalance(requestDto.getInitialDeposit());
        return responseDto;
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

    public Boolean getIfAccountExistOfUser(String userId, String accountNumber) {

        return accountRepository.existsByUserIdAndAccountNumberAndStatus(userId, accountNumber, Status.ACTIVE);
    }

    public Boolean getIfAccountExistAndActive(String accountNumber) {

        return accountRepository.existsByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
    }

    public Boolean getIfValidBalance(Double amount, String accountNumber){

        Account account = accountRepository.findByAccountNumber(accountNumber).orElse(null);
        if (account == null){
            return false;
        }

        return account.getBalance() >= amount;
    }



    @Transactional
    public void completeTransaction(String fromAccountNumber, String toAccountNumber, Double amount) {

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);


        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(() -> new AccountNotFoundException("Receiver Account not found"));

        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(toAccount);

    }

    public void deposit(String accountNumber, Double amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Deposit account not found"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

    }

    public void withdraw(String accountNumber, Double amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("withdraw account not found"));
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

    }
}
