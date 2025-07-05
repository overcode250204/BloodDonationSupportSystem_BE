package com.example.BloodDonationSupportSystem.dto.healthcheckDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HealthCheckRequest {
    private UUID healthCheckId;
    private String healthStatus;
    private float height;
    private float weight;
    private String note;
    private UUID registrationId;
}
