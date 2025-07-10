package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.reportDTO.OverviewReportDTO;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequest;
import com.example.BloodDonationSupportSystem.dto.reportDTO.ReportFilterRequestByDate;
import com.example.BloodDonationSupportSystem.service.reportservice.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/report")
@Tag(name = "Report Controller")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/blood-inventory/export")
    public void exportDonationReport( HttpServletResponse response) throws IOException {

        reportService.exportBloodInventoryReportToExcel( response);
    }

    @PostMapping("/overview")
    public BaseReponse<OverviewReportDTO> getOverviewReport(@RequestBody @Valid ReportFilterRequest request) {
        var overview = reportService.getOverview(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Overview Report", overview);
    }



    @PostMapping("/monthly-statistic")
    public BaseReponse<?> getMonthlyStats(@RequestBody @Valid ReportFilterRequest request) {
        var monthlyData = reportService.getMonthlyStats(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Monthly Stats", Map.of("monthlyData", monthlyData));
    }

    @PostMapping("/cumulative-volume")
    public BaseReponse<?> getCumulativeVolume(@RequestBody @Valid ReportFilterRequest request) {
        var cumulativeData = reportService.getCumulativeVolume(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Cumulative Volume Report", cumulativeData);
    }

    @PostMapping("/blood-donation")
    public BaseReponse<?> getBloodDonationReport(@RequestBody @Valid ReportFilterRequestByDate request) {
        var getBloodDonationReport = reportService.getDonationReport(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Blood Donation Report", getBloodDonationReport);
    }

    @PostMapping("/blood-donation/export")
    public void exportDonationReport(@RequestBody @Valid ReportFilterRequestByDate request, HttpServletResponse response) throws IOException {

        reportService.exportBloodDonationReportToExcel( request, response);
    }
}
