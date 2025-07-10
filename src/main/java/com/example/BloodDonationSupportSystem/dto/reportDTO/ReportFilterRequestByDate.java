package com.example.BloodDonationSupportSystem.dto.reportDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilterRequestByDate {
    @NotBlank(message = "Start Date can not empty")
    private String startDate;
    @NotBlank(message = "Start Date can not empty")
    private String endDate;
}
