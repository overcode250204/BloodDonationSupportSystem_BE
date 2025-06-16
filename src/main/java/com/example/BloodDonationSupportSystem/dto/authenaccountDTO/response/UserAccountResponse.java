package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountResponse {
    private UUID userId;
    private String fullName;
    private LocalDate dayOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String bloodType;
    private String status;
    private String roleName;
}
