package com.example.BloodDonationSupportSystem.dto.reportDTO;

import lombok.Data;

import java.util.Date;

@Data
public class ReportFilterRequest {
    private Date fromDate;
    private Date toDate;
}
