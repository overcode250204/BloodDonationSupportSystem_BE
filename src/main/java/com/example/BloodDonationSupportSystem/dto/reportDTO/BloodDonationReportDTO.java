package com.example.BloodDonationSupportSystem.dto.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodDonationReportDTO {
    private String reportDate;
    private String hospitalAddress;
    private int numberRegistration;
    private int numberSuccess;
    private int numberFailed;
    private int volumeBlood;
}
