package com.example.account_service.exceptions;

import com.example.account_service.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> handleValidationExceptions(MethodArgumentNotValidException ex){

        List<ErrorResponseDto> errors = ex.getBindingResult().getFieldErrors().stream().map(error ->
                new ErrorResponseDto(error.getDefaultMessage())
        ).toList();

        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadable(HttpMessageNotReadableException ex){

        String message = "Invalid request format or enum value";

        // Optional: customize message if it's an enum issue
        if (ex.getMessage().contains("AccountType")) {
            message = "Invalid value for AccountType. Allowed values: SAVINGS, CURRENT, LOAN";
        }

        return ResponseEntity.badRequest().body(new ErrorResponseDto(message));
    }

    @ExceptionHandler(AccountWithThisUserIdAndAccountTypeAlreadExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountWithUserIdAndAccountTypeExists(AccountWithThisUserIdAndAccountTypeAlreadExistsException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(AccountDoesNotBelongToThisUserException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountDoesNotBelongToThisUser(AccountDoesNotBelongToThisUserException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountNotFound(AccountNotFoundException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedAccess(UnauthorizedAccessException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage()));
    }
}
