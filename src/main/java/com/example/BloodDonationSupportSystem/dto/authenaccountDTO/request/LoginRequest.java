package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @Pattern(regexp = "^(03|05|07|08|09)\\d{8}$", message = "Invalid phonenumber (must be start with 03, 05, 07, 08, 09 and has 10 numbers)")
    @NotNull
    @Length(max = 10)
    private String phoneNumber;
    @NotNull
    @Length(max = 10)
    private String password;
}
