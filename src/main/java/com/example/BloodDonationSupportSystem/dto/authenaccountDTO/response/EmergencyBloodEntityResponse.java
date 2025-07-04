package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyBloodEntityResponse {
    private UUID emergencyBloodRequestId;
    private String patientName;
    private String locationOfPatient;
    private String bloodType;
    private int volumeMl;
    private String levelOfUrgency;
}
