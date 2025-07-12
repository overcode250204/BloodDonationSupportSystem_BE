package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;
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
import java.util.List;
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

    @PostMapping("/monthly-blood-statistic")

    public BaseReponse<?> getMonthlyStats(@RequestBody @Valid ReportFilterRequest request) {
        var monthlyData = reportService.getMonthlyStats(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Monthly Stats", Map.of("monthlyData", monthlyData));
    }

    @PostMapping("/blood-inventory-for-chart")
    public BaseReponse<?> getBloodVolume(@RequestBody @Valid ReportFilterRequest request) {
        var cumulativeData = reportService.getBloodVolume(request);
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

    @GetMapping("/blood-inventory")
    public BaseReponse<?> getBloodInventory() {
        List<BloodInventoryResponse> bloodInventory = reportService.getBloodInventory();

        return new BaseReponse<>(HttpStatus.OK.value(), "Get blood inventory", bloodInventory);
    }


}
