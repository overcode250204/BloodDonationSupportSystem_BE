package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String fullName;
    private LocalDate dayOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String bloodType;
}
