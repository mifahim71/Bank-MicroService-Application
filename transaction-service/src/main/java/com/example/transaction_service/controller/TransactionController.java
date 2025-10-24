package com.example.transaction_service.controller;

import com.example.transaction_service.dto.*;
import com.example.transaction_service.entities.Transaction;
import com.example.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(
            @RequestHeader("user-id") String userId,
            @RequestHeader("user-email") String email,
            @RequestHeader("user-role") String role
    ){

        return ResponseEntity.ok().body("user with userId: "+userId+" | email: "+email+" | role: "+role+" |");
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponseDto>> getAccountTransactions(
            @PathVariable String accountNumber,
            @RequestHeader("user-id") String userId
    ){

        List<TransactionResponseDto> responseDtos = transactionService.getAccountTransactions(accountNumber, userId);
        return ResponseEntity.ok().body(responseDtos);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferCreateResponseDto> transferAmount(
            @Valid @RequestBody TransferCreateRequestDto requestDto,
            @RequestHeader("user-id") String userId
    ){
        TransferCreateResponseDto responseDto = transactionService.createTransactionRequest(requestDto, userId);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDto> deposit(
            @Valid @RequestBody DepositRequestDto requestDto,
            @RequestHeader("user-id") String userId
    ){

        DepositResponseDto responseDto = transactionService.deposit(requestDto, userId);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> initailDeposit(
            @RequestParam String accountNumber,
            @RequestParam Double amount
    ){
        transactionService.depositRequest(accountNumber, amount);
        return ResponseEntity.ok().body("initial Deposit successful");
    };

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponseDto> withdraw(
            @Valid @RequestBody WithdrawRequestDto requestDto,
            @RequestHeader("user-id") String userId
    ){

        WithdrawResponseDto responseDto = transactionService.withdraw(requestDto, userId);
        return ResponseEntity.ok().body(responseDto);
    }

}
