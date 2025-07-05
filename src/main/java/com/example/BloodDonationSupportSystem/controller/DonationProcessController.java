package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.donationprocessDTO.request.DonationProcessRequest;
import com.example.BloodDonationSupportSystem.dto.donationprocessDTO.response.DonationProcessResponse;
import com.example.BloodDonationSupportSystem.dto.healthcheckDTO.request.HealthCheckRequest;
import com.example.BloodDonationSupportSystem.service.donationprocess.DonationProcessService;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/staff")
@Tag(name = "Donation Process Controller")
public class DonationProcessController {

    @Autowired
    private DonationProcessService donationProcessService;

    @GetMapping("/process-list")
    public BaseReponse<List<DonationProcessResponse>> getDonationProcessForCurrentStaff() {
        UUID staffId = UUID.fromString(AuthUtils.getCurrentUser().getUsername());
        List<DonationProcessResponse> list = donationProcessService.getDonationProcessByStaffId(staffId);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get donation process list successfully", list);
    }

    @PutMapping("/update-process")
    public BaseReponse<?> updateDonationProcess(@RequestBody @Valid DonationProcessRequest request) {
        try {
            donationProcessService.updateDonationProcess(request);
            return new BaseReponse<>(HttpStatus.OK.value(), "Update successfully", null);
        } catch (RuntimeException e) {
            return new BaseReponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }

}
