package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.reportDTO.OverviewReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequest;
import com.example.BloodDonationSupportSystem.service.reportservice.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/report")
@Tag(name = "Report Controller")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/blood-inventory/export")
    public void exportDonationReport( HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Kho_Mau.xlsx");
        reportService.exportBloodInventoryReportToExcel( response.getOutputStream());
    }

    @PostMapping("/overview")
    public BaseReponse<OverviewReportDTO> getOverviewReport(@RequestBody ReportFilterRequest request) {
        var overview = reportService.getOverview(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Overview Report", overview);
    }



}
