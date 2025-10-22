package com.auth.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDto {

    @NotBlank(message = "email required")
    @Email(message = "valid email address required")
    private String email;

    @NotBlank(message = "password required")
    @Size(min = 8, message = "at least 8 characters required")
    private String password;
}
