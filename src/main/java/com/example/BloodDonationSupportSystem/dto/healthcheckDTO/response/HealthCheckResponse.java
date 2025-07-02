package com.example.BloodDonationSupportSystem.dto.healthcheckDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HealthCheckResponse {
    private UUID healthCheckId;
    private String donorFullName;
    private LocalDate registrationDate;
    private String levelOfUrgency;
    private String registrationStatus;
    private String healthStatus;
    private float height;
    private float weight;
    private UUID donationRegistrationId;
    private UUID screenedByStaffId;
}
