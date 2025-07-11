package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodVolumeRequest {
    @NotNull(message = "Donation registration ID cannot be blank")
    private UUID donationRegisId;
    @NotNull(message = "Donation process ID cannot be blank")
    private UUID processId;
    @Min(value = 250, message = "Total volume must be at least 250ml")
    @Max(value = 450, message = "Total volume must not exceed 450ml")
    private int volumeMl;

}
