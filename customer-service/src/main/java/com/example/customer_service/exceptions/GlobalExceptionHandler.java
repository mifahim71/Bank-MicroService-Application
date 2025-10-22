package com.example.customer_service.exceptions;

import com.example.customer_service.dto.ErrorResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrationViolation(DataIntegrityViolationException ex){

        ErrorResponseDto responseDto = new ErrorResponseDto();
        responseDto.setMessage("Duplicate value or constraint violation");

        if(ex.getMessage().contains("email")){
            responseDto.setMessage("Email address already exists");
        }

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserId(DuplicateUserIdException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(CustomerIdNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerIdNotFound(CustomerIdNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedAccess(UnauthorizedAccessException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto(ex.getMessage()));
    }
}
