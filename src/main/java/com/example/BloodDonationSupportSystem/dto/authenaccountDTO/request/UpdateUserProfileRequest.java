package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserProfileRequest {
    @NotNull(message = "FullName can't null")
    private String fullName;

    @NotNull(message = "Address can't null")
    private String address;

    @NotNull(message = "Day of birth can't null")
    @Past(message = "Day of birth must be past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender can't null")
    private String gender;
}
