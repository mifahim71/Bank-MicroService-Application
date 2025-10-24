package com.example.transaction_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service", url = "http://localhost:4003/account")
public interface AccountClient {

    @GetMapping("/checkIfAccountExistOfUser")
    Boolean checkIfAccountExistOfUser(
            @RequestParam String userId,
            @RequestParam String accountNumber
    );

    @GetMapping("/checkIfAccountExistAndActive")
    Boolean checkIfAccountExistAndActive(
            @RequestParam String accountNumber
    );

    @GetMapping("/checkIfValidBalance")
    Boolean checkIfValidBalance(
            @RequestParam Double amount,
            @RequestParam String accountNumber
    );

    @PutMapping("/complete-transaction")
    String reduceBalance(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam Double amount
    );

    @PutMapping("/deposit")
    String deposit(
            @RequestParam String accountNumber,
            @RequestParam Double amount
    );

    @PutMapping("/withdraw")
    String withdraw(
            @RequestParam String accountNumber,
            @RequestParam Double amount
    );
}
