package com.example.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

    private String customerId;

    private String userId;

    private String name;

    private String email;

    private String address;

    private String phone;
}
