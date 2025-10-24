package com.example.transaction_service.exceptions;

public class AccountNumberInValidException extends RuntimeException {
    public AccountNumberInValidException(String message) {
        super(message);
    }
}
