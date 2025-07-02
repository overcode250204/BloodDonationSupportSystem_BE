package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.DonationRegistrationDTO;
import com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.request.DonationRegistrationUpdateStatusRequest;
import com.example.BloodDonationSupportSystem.service.donationregistrationservice.DonationRegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Donation Registration Controller")
public class DonationRegistrationController {

    @Autowired
    private DonationRegistrationService donationRegistrationService;

    @PostMapping("/member/registration")
    public BaseReponse<?> create(@RequestBody @Valid DonationRegistrationDTO dto) {
        DonationRegistrationDTO response = donationRegistrationService.create(dto);
        return new BaseReponse<>(HttpStatus.OK.value(), "Created successfully", response);
    }

    @PutMapping("/member/registration/{id}")
    public BaseReponse<?> update(@PathVariable UUID id, @RequestBody @Valid DonationRegistrationDTO dto) {
        DonationRegistrationDTO response = donationRegistrationService.update(id, dto);
        return new BaseReponse<>(HttpStatus.OK.value(), "Updated successfully", response);
    }

    @PutMapping("/staff/cancel-registration")
    public ResponseEntity<?> updateRegistrationStatus(@RequestBody DonationRegistrationUpdateStatusRequest request) {
        try {
            donationRegistrationService.updateCancelStatus(request.getDonationRegistrationId(), request.getStatus());
            return ResponseEntity.ok("Update Successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the status.");
        }
    }

    @DeleteMapping("/member/registration/{id}")
    public BaseReponse<?> delete(@PathVariable UUID id) {
        donationRegistrationService.delete(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "deleted successfully", null);
    }

    @GetMapping("/member/registration/{id}")
    public BaseReponse<?> getById(@PathVariable UUID id) {
        DonationRegistrationDTO response = donationRegistrationService.getById(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "Fetched successfully", response);
    }

    @GetMapping("/member/registrations")
    public BaseReponse<?> getAll() {
        List<DonationRegistrationDTO> response = donationRegistrationService.getAll();
        return new BaseReponse<>(HttpStatus.OK.value(), "Fetched all successfully", response);
    }


}
