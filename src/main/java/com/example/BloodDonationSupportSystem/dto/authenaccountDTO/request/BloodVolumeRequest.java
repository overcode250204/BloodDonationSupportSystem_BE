package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodVolumeRequest {
    @Min(value = 250, message = "Total volume must be at least 250ml")
    @Max(value = 450, message = "Total volume must not exceed 450ml")
    private int volumeMl;
}
