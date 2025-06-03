package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @NotBlank
    @NotNull
    @Length(max = 10)
    private String phoneNumber;

    @NotBlank
    @NotNull
    @Length(max = 10)
    private String password;
}
