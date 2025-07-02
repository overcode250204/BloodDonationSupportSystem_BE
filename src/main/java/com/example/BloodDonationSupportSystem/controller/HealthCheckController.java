package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.healthcheckDTO.request.HealthCheckRequest;
import com.example.BloodDonationSupportSystem.dto.healthcheckDTO.response.HealthCheckResponse;
import com.example.BloodDonationSupportSystem.service.healthcheckservice.HealthCheckService;
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
@Tag(name = "Health Check Controller")
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("/health-checks")
    public ResponseEntity<List<HealthCheckResponse>> getHealthChecksForCurrentStaff() {
        UUID staffId = UUID.fromString(AuthUtils.getCurrentUser().getUsername());
        List<HealthCheckResponse> list = healthCheckService.getHealthChecksByStaffId(staffId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateHealthCheck(@RequestBody @Valid HealthCheckRequest request) {
        try {
            healthCheckService.updateHealthCheck(request);
            return ResponseEntity.ok("Update Succesfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }



}
