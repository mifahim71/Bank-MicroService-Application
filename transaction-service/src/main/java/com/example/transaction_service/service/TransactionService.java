package com.example.transaction_service.service;

import com.example.transaction_service.client.AccountClient;
import com.example.transaction_service.dto.*;
import com.example.transaction_service.entities.Transaction;
import com.example.transaction_service.enums.Status;
import com.example.transaction_service.enums.Type;
import com.example.transaction_service.exceptions.AccountNumberInValidException;
import com.example.transaction_service.mapper.TransactionMapper;
import com.example.transaction_service.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountClient accountClient;

    private final TransactionMapper transactionMapper;

    public TransferCreateResponseDto createTransactionRequest(@Valid TransferCreateRequestDto requestDto, String userId) {

        if(Boolean.FALSE.equals(accountClient.checkIfAccountExistOfUser(userId, requestDto.getFromAccount()))){
            throw new AccountNumberInValidException("Account Number: "+requestDto.getFromAccount()+" is not belongs to this user");
        }

        if(Boolean.FALSE.equals(accountClient.checkIfAccountExistAndActive(requestDto.getToAccount()))){
            throw new AccountNumberInValidException("Account is not available or blocked");
        }

        if(Boolean.FALSE.equals(accountClient.checkIfValidBalance(requestDto.getAmount(), requestDto.getFromAccount()))){
            throw new AccountNumberInValidException("Insufficient Balance");
        }


        String message = accountClient.reduceBalance(requestDto.getFromAccount(), requestDto.getToAccount(), requestDto.getAmount());
        System.out.println(message);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .amount(requestDto.getAmount())
                .type(Type.TRANSFER)
                .fromAccount(requestDto.getFromAccount())
                .toAccount(requestDto.getToAccount())
                .status(Status.SUCCESS)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransferCreateResponseDto(savedTransaction);

    }

    @Transactional
    public DepositResponseDto deposit(@Valid DepositRequestDto requestDto, String userId) {

        if(Boolean.FALSE.equals(accountClient.checkIfAccountExistOfUser(userId, requestDto.getAccountNumber()))){
            throw new AccountNumberInValidException("Account Number: "+requestDto.getAccountNumber()+" is not belongs to this user");
        }

        String deposit = accountClient.deposit(requestDto.getAccountNumber(), requestDto.getAmount());
        log.info(deposit);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .status(Status.SUCCESS)
                .amount(requestDto.getAmount())
                .toAccount(requestDto.getAccountNumber())
                .type(Type.DEPOSIT)
                .build();


        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.depositResponseDto(savedTransaction);
    }

    public WithdrawResponseDto withdraw(@Valid WithdrawRequestDto requestDto, String userId) {

        if(Boolean.FALSE.equals(accountClient.checkIfAccountExistOfUser(userId, requestDto.getAccountNumber()))){
            throw new AccountNumberInValidException("Account Number: "+requestDto.getAccountNumber()+" is not belongs to this user");
        }

        if(Boolean.FALSE.equals(accountClient.checkIfValidBalance(requestDto.getAmount(), requestDto.getAccountNumber()))){
            throw new AccountNumberInValidException("Insufficient Balance");
        }

        String withdraw = accountClient.withdraw(requestDto.getAccountNumber(), requestDto.getAmount());
        log.info(withdraw);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .status(Status.SUCCESS)
                .amount(requestDto.getAmount())
                .fromAccount(requestDto.getAccountNumber())
                .type(Type.WITHDRAW)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.withdrawResponseDto(savedTransaction);

    }

    public List<TransactionResponseDto> getAccountTransactions(String accountNumber, String userId) {

        if(Boolean.FALSE.equals(accountClient.checkIfAccountExistOfUser(userId, accountNumber))){
            throw new AccountNumberInValidException("Account Number: "+accountNumber+" is not belongs to this user");
        }

        List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);

        return transactions.stream().map(transactionMapper::transactionResponseDto).toList();
    }

    @Transactional
    public void depositRequest(String accountNumber, Double amount) {

        String deposit = accountClient.deposit(accountNumber, amount);
        log.info(deposit);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .status(Status.SUCCESS)
                .amount(amount)
                .toAccount(accountNumber)
                .type(Type.DEPOSIT)
                .build();


        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionMapper.depositResponseDto(savedTransaction);
    }
}
