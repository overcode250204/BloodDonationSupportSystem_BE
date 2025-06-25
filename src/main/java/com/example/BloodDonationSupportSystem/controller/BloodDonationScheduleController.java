package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.request.BloodDonationScheduleRequest;
import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.response.BloodDonationScheduleResponse;
import com.example.BloodDonationSupportSystem.service.blooddonationscheduleservice.BloodDonationScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff/schedule")
@Tag(name = "Blood Donation Schedule Controller")
public class BloodDonationScheduleController {

    @Autowired
    private BloodDonationScheduleService bloodDonationScheduleService;

    @PostMapping("/create")
    public BaseReponse<BloodDonationScheduleResponse> create(@RequestBody @Valid BloodDonationScheduleRequest request) {
         var response = bloodDonationScheduleService.createBloodDonationSchedule(request);
         return new BaseReponse<>(HttpStatus.OK.value(), "Create Schedule", response);
    }

    @GetMapping("/all")
    public BaseReponse<?> getAllBloodDonationSchedule() {
        var response = bloodDonationScheduleService.getAllBloodDonationSchedule();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get All Blood Donation Schedule", response);
    }

    @GetMapping("/{id}")
    public BaseReponse<?> getBloodDonationSchedule(@PathVariable UUID id) {
        var response = bloodDonationScheduleService.getBloodDonationSchedule(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get Blood Donation Schedule", response);
    }

    @DeleteMapping("/delete/{id}")
    public BaseReponse<?> delete(@PathVariable UUID id) {
        bloodDonationScheduleService.deleteBloodDonationSchedule(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "Delete Schedule", null);
    }

    @PutMapping("/update/{id}")
    public BaseReponse<?> update(@PathVariable UUID id, @RequestBody @Valid BloodDonationScheduleRequest request) {
        var response = bloodDonationScheduleService.updateBloodDonationSchedule(id, request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update Schedule", response);
    }
}
