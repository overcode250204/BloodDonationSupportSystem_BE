package com.example.BloodDonationSupportSystem.dto.donationprocessDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonationProcessRequest {
    private UUID donationProcessId;
    private String processStatus;
    private int volumeMl;
    private UUID donationRegistrationId;
}
