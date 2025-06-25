package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO;
import com.example.BloodDonationSupportSystem.service.userservice.DonationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/member")
public class DonationInfoController {

    @Autowired
    private DonationInfoService donationInfoService;

    @GetMapping("/donation-info")
    public BaseReponse<?> getDonationInfo() {
        List<DonorDonationInfoDTO> donationInfo = donationInfoService.getDonationInfo();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get donation info successful", donationInfo);
    }

    @PutMapping("/donation-info/cancel/{registrationId}")
    public BaseReponse<?> cancelDonation(@PathVariable UUID registrationId) {
        donationInfoService.cancelDonation(registrationId);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update donation info status successful", null);
    }

    @GetMapping("/donation-info/{registrationId}")
    public BaseReponse<?> updateDonationInfo(@PathVariable UUID registrationId) {
        DonorDonationInfoDTO donationInfo = donationInfoService.getDonationInfoById(registrationId);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get donation info successful", donationInfo);
    }

}
