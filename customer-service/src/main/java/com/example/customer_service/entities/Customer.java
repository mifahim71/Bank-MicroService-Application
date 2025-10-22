package com.example.customer_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    @NotBlank(message = "Customer Name is Required")
    private String name;

    @NotBlank(message = "Customer Email is Required")
    private String email;

    @NotBlank(message = "Customer address is Required")
    private String address;

    @NotBlank(message = "Customer phone is Required")
    private String phone;

    @NotNull(message = "Date of birth is Required")
    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;
}
