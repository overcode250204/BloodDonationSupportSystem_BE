package com.example.BloodDonationSupportSystem.dto.processmanagement;


import com.example.BloodDonationSupportSystem.enumentity.processmanagement.HealthStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateToCollectingRequest {

    @NotNull
    @JsonProperty("member_screening_id")
    private UUID member_screening_id;

    @JsonProperty("health_status")
    @NotNull
    private HealthStatusEnum health_status;
}
