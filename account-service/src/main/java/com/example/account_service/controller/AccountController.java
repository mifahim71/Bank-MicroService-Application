package com.example.account_service.controller;

import com.example.account_service.dto.*;
import com.example.account_service.exceptions.UnauthorizedAccessException;
import com.example.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().body("working fine");
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> getAccount(
            @PathVariable String accountNumber,
            @RequestHeader("user-id") String userId
    ){

        AccountResponseDto responseDto = accountService.getAccountByAccountNumber(accountNumber, userId);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccountOfUser(
            @RequestHeader("user-id") String userId
    ){
        List<AccountResponseDto> responseDtos = accountService.getAllAccountOfUser(userId);

        return ResponseEntity.ok().body(responseDtos);
    }

    @PostMapping
    public ResponseEntity<AccountCreateResponseDto> createAccount(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody AccountCreateRequestDto requestDto
    ){

       AccountCreateResponseDto responseDto =  accountService.createAccount(requestDto, userId);

       return ResponseEntity.ok().body(responseDto);

    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountStatusUpdateResponseDto> updateStatus(
            @Valid @RequestBody AccountStatusUpdateRequestDto requestDto,
            @PathVariable String accountNumber,
            @RequestHeader("user-role") String role
    ){
        if(!role.equals("ADMIN")){
            throw new UnauthorizedAccessException("Access denied for role User");
        }

        AccountStatusUpdateResponseDto responseDto = accountService.updateStatus(requestDto, accountNumber);

        return ResponseEntity.ok().body(responseDto);
    }

}
