package com.example.account_service.exceptions;

public class AccountWithThisUserIdAndAccountTypeAlreadExistsException extends RuntimeException {
    public AccountWithThisUserIdAndAccountTypeAlreadExistsException(String message) {
        super(message);
    }
}
