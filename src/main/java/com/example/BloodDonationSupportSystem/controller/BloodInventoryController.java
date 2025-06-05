package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.entity.DonationRegisterationFake;
import com.example.BloodDonationSupportSystem.service.BloodInventoryService.BloodInventoryService;
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
    //truyền các giá trị vào túi máu :
    // bloodType có thể null, so luong,volume ko dc null, status gán cứng RECEIVED , createdAt ko dc null, DonationRegistrationId
    public BaseReponse<BloodBagResponse> createBloodBag(@RequestBody BloodBagRequest bloodBagRequest) {
         BloodBagResponse bloodBagResponse= bloodBagService.createBloodBag(bloodBagRequest);
        System.out.println("tao tui mau thanh cong");
         return new BaseReponse<>(HttpStatus.OK.value(), "Blood bag created successfully", bloodBagResponse);

    }

    @PostMapping("/create-fake-donation")
    public BaseReponse<?> createFakeDonation(@RequestBody DonationRegisterationFake donationRegisterationFake) {
        var fakeDonation = bloodBagService.createFakeDonation(donationRegisterationFake);
        return new BaseReponse<>(HttpStatus.OK.value(), "Fake donation created successfully", fakeDonation);
    }
}
