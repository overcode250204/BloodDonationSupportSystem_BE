package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.BloodDonationScheduleDTO;
import com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.StatByDateDTO;
import com.example.BloodDonationSupportSystem.service.scheduleservice.BloodDonationScheduleService;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
@Tag(name = "Schedule")
public class BloodDonationScheduleController {

    @Autowired
    private BloodDonationScheduleService bloodDonationScheduleService;

    @GetMapping("/registration/stat-by-day")
    public BaseReponse<?> getStatByDay() {
        List<StatByDateDTO> response = bloodDonationScheduleService.getStatByDate();
        return new BaseReponse<>(HttpStatus.OK.value(), "Count registration in each day", response);
    }

    @PostMapping("/schedule")
    public BaseReponse<?> createSchedule(@RequestBody @Valid BloodDonationScheduleDTO dto) {
        UUID staffId = UUID.fromString(AuthUtils.getCurrentUser().getUsername());
        String response = bloodDonationScheduleService.createSchedule(dto, staffId);

        return new BaseReponse<>(HttpStatus.OK.value(), response, null);
    }


}
