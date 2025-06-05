package com.example.BloodDonationSupportSystem.dto.processmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateToScreeningRequest {

    @NotNull
    @JsonProperty("member_screening_id")
    private UUID member_screening_id;
}
