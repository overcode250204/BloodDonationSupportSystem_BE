package com.example.BloodDonationSupportSystem.dto.reportDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffEmergencyReportDTO {

//    {
//        "staffName": "Trần Thị B",
//            "staffPhone": "0987654321",
//            "patientName": "Nguyễn Văn X",
//            "patientPhone": "0123456789",
//            "donorName": "Phạm Thị Y",
//            "donorPhone": "0987654321",
//            "status": "Đã xử lý"
//    },
    private String staffName;
    private String staffPhoneNumber;
    private String patientName;
    private String patientPhoneNumber;
    private String note;
    private String donorName;
    private String donorPhoneNumber;
    private String donorEmail;
    private Date donationDate;
    private String status;
}
