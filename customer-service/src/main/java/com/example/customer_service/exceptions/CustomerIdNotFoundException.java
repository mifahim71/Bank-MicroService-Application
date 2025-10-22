package com.example.customer_service.exceptions;

public class CustomerIdNotFoundException extends RuntimeException {
    public CustomerIdNotFoundException(String message) {
        super(message);
    }
}
