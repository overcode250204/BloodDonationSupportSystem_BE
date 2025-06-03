package com.example.BloodDonationSupportSystem.controller;


import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCollectingRequest;
import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCompletedRequest;
import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToScreeningRequest;
import com.example.BloodDonationSupportSystem.entity.BloodDonationProcess;
import com.example.BloodDonationSupportSystem.entity.MemberScreening;

import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.MemberScreeningStatus;
import com.example.BloodDonationSupportSystem.service.processmanagement.BloodDonationProcessService;
import com.example.BloodDonationSupportSystem.service.processmanagement.MemberScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/process-management")
public class ProcessManagementController {

    @Autowired
    private MemberScreeningService memberScreeningService;

    @Autowired
    private BloodDonationProcessService bloodDonationProcessService;

    @GetMapping("/pending")
    public List<MemberScreening> getPending() {
        return memberScreeningService.findByStatus(MemberScreeningStatus.PENDING);
    }

    @GetMapping("/screening")
    public List<MemberScreening> getScreening() {
        return memberScreeningService.findByStatus(MemberScreeningStatus.SCREENING);
    }

    @GetMapping("/collecting")
    public List<BloodDonationProcess> getCollecting(){
        return bloodDonationProcessService.findByStatus(BloodDonationProcessStatus.COLLECTING);
    }

    @GetMapping("/completed")
    public List<BloodDonationProcess> getCompleted() {
        return bloodDonationProcessService.findByStatus(BloodDonationProcessStatus.COMPLETED);
    }

    @PutMapping("/update-to-screening")
    public ResponseEntity<String> updateToScreening(@RequestBody UpdateToScreeningRequest req) {
        String responseMessage = memberScreeningService.updateToScreening(req);
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/update-to-collecting")
    public ResponseEntity<String> updateToCollecting(@RequestBody UpdateToCollectingRequest req){
        String responseMessage = memberScreeningService.updateToCollecting(req);
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/update-to-completed")
    public ResponseEntity<String> updateToCompleted(@RequestBody UpdateToCompletedRequest req){
        String responseMessage = bloodDonationProcessService.updateToCompleted(req);
        return ResponseEntity.ok(responseMessage);
    }
}
