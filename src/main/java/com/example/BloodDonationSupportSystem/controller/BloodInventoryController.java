package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.BloodInventoryService.BloodInventoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/staff")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodBagService;
    @PostMapping("/create-blood-bag")

    public BaseReponse<BloodBagResponse> createBloodBag(@RequestBody @Valid BloodBagRequest bloodBagRequest) {
         BloodBagResponse bloodBagResponse= bloodBagService.createBloodBag(bloodBagRequest);

         return new BaseReponse<>(HttpStatus.OK.value(), "Blood bag created successfully", bloodBagResponse);

    }


}
