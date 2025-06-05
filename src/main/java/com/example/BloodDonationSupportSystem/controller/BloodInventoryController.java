package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.BloodInventoryService.BloodBagService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/staff")
public class BloodBagController {

    @Autowired
    private BloodBagService bloodBagService;
    @PostMapping("/create-blood-bag")
    //truyền các giá trị vào túi máu :
    // bloodType có thể null, volume ko dc null, status gán cứng RECEIVED , createAtDate ko dc null, bloodDonationHistoryId chỗ này tạo HistoryId giả r
    public BaseReponse<BloodBagResponse> createBloodBag(@RequestBody BloodBagRequest bloodBagRequest) {
         BloodBagResponse bloodBagResponse= bloodBagService.createBloodBag(bloodBagRequest);
        System.out.println("tao tui mau thanh cong");
         return new BaseReponse<>(HttpStatus.OK.value(), "Blood bag created successfully", bloodBagResponse);

    }
    @PostMapping("/helloV2")
    public String helloV2() {
        return "hello Quang staff V2";
    }
}
