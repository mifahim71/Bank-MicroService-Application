package com.example.account_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service", url = "http://localhost:4004/transaction")
public interface TransactionClient {

    @PutMapping("/deposit")
    String deposit(
            @RequestParam String accountNumber,
            @RequestParam Double amount
    );
}
