package com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DonationRegistrationUpdateStatusRequest {

    @NotNull(message = "Donation Registration ID is null")
    private UUID donationRegistrationId;

    @NotNull(message = "Status is null")
    private String status;
}
