package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequest;
import com.example.BloodDonationSupportSystem.service.reportservice.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/report")
@Tag(name = "Report Controller")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/donation-registration/export")
    public void exportDonationReport(@RequestBody ReportFilterRequest filter, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=donation_registration_report.xlsx");
        reportService.exportDonationReportToExcel(filter, response.getOutputStream());
    }


}
