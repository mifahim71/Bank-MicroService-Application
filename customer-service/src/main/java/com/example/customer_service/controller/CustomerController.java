package com.example.customer_service.controller;

import com.example.customer_service.dto.*;
import com.example.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/health-check")
    public String heathCheck(
            @RequestHeader("user-email") String email,
            @RequestHeader("user-id") String userId,
            @RequestHeader("user-role") String role
    ){
        return "userId: "+userId+" | email: "+email+" | role: "+role;
    }

    @PostMapping()
    public ResponseEntity<CustomerCreateResponseDto> createCustomer(
           @Valid @RequestBody CustomerCreateRequestDto requestDto,
           @RequestHeader("user-email") String email,
           @RequestHeader("user-id") String userId,
           @RequestHeader("user-role") String role
    ){
        CustomerCreateResponseDto responseDto = customerService.createCustomer(requestDto, userId, email);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(
            @PathVariable UUID id,
            @RequestHeader("user-email") String email,
            @RequestHeader("user-id") String userId,
            @RequestHeader("user-role") String role
    ){
        CustomerResponseDto responseDto = customerService.getCustomerById(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping()
    public ResponseEntity<CustomerResponseDto> getCustomerByUserId(
            @RequestHeader("user-email") String email,
            @RequestHeader("user-id") String userId,
            @RequestHeader("user-role") String role
    ){
        CustomerResponseDto responseDto = customerService.getCustomerByUserId(userId);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CustomerResponseDto>> findAll(
            @RequestHeader("user-email") String email,
            @RequestHeader("user-id") String userId,
            @RequestHeader("user-role") String role
    ){

        List<CustomerResponseDto> responseDtos = customerService.findAll(role);
        return ResponseEntity.ok().body(responseDtos);
    }

    @PutMapping
    public ResponseEntity<CustomerUpdateResponseDto> updateCustomer(
            @RequestBody CustomerUpdateRequestDto requestDto,
            @RequestHeader("user-id") String userId
    ){

        CustomerUpdateResponseDto responseDto = customerService.updateCustomer(requestDto, userId);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(
            @RequestHeader("user-id") String userId
    ){
        customerService.deleteCustomer(userId);
        return ResponseEntity.ok().body("Customer with userId: "+userId+" is deleted successfully");
    }
}
