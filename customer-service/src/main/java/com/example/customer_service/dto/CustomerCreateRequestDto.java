package com.example.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateRequestDto {

    @NotBlank(message = "Customer Name is required")
    private String name;

    @NotBlank(message = "Customer address is required")
    private String address;

    @NotBlank(message = "Customer phone is required")
    private String phone;

    @NotBlank(message = "Customer date of birth is required")
    private String dob;
}
