package com.example.account_service.exceptions;

public class AccountDoesNotBelongToThisUserException extends RuntimeException {
    public AccountDoesNotBelongToThisUserException(String message) {
        super(message);
    }
}
