package com.auth.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "email required")
    @Email(message = "valid email address required")
    private String email;

    @NotBlank(message = "password required")
    @Size(min = 8, message = "minimum 8 characters required")
    private String password;
}
