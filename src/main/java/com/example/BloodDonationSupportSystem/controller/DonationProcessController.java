package com.example.BloodDonationSupportSystem.controller;

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
    public ResponseEntity<List<DonationProcessResponse>> getDonationProcessForCurrentStaff() {
        UUID staffId = UUID.fromString(AuthUtils.getCurrentUser().getUsername());
        List<DonationProcessResponse> list = donationProcessService.getDonationProcessByStaffId(staffId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update-process")
    public ResponseEntity<?> updateDonationProcess(@RequestBody @Valid DonationProcessRequest request) {
        try {
            donationProcessService.updateDonationProcess(request);
            return ResponseEntity.ok("Update Succesfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
