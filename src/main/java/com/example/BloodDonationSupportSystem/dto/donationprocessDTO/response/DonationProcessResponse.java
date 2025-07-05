package com.example.BloodDonationSupportSystem.dto.donationprocessDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonationProcessResponse {
    private UUID donationProcessId;
    private String donorFullName;
    private LocalDate registrationDate;
    private String levelOfUrgency;
    private String registrationStatus;
    private String processStatus;
    private String note;
    private UUID donationRegistrationId;
    private UUID screenedByStaffId;
}
