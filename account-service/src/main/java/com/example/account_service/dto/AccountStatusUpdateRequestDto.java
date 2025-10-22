package com.example.account_service.dto;

import com.example.account_service.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusUpdateRequestDto {

    @NotNull(message = "Status is required")
    private Status status;
}
