package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodVolumeRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonationProcessResponse;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.service.bloodinventoryservice.BloodInventoryService;
import com.example.BloodDonationSupportSystem.service.donationprocesservice.BloodDonationProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/staff")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodBagService;

    @Autowired
    private BloodDonationProcessService donationProcessService;

    @GetMapping("/get-total-blood-volume-of-all-blood-types")
    public BaseReponse<List<BloodInventoryResponse>> getBloodBagList() {
        List<BloodInventoryResponse> bloodBagList = bloodBagService.getBloodBagList();

        return new BaseReponse<>(HttpStatus.OK.value(), "Get total of blood volume successfully", bloodBagList);
    }

    @GetMapping("/get-information-of-a-blood-type/{bloodTypeId}")
    public BaseReponse<BloodInventoryResponse> getABloodBagByTypeId(@PathVariable String bloodTypeId) {
        BloodInventoryResponse bloodBagTotal = bloodBagService.getBloodBagById(bloodTypeId);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get information a blood type successfully", bloodBagTotal);
    }

    @PutMapping("/update-blood-volume/{bloodTypeId}")
    public BaseReponse<BloodInventoryResponse> updateBloodVolume(@PathVariable String bloodTypeId,
                                                                 @RequestBody BloodVolumeRequest bloodVolumeRequest){
        BloodInventoryResponse bloodBagUpdate = bloodBagService.updateBloodVolume(bloodTypeId, bloodVolumeRequest.getVolumeMl());
        return new BaseReponse<>(HttpStatus.OK.value(), "Update blood volume successfully",bloodBagUpdate);
    }


    @GetMapping("/get-completed-donation-process-list/blood-checking")
    public BaseReponse<List<DonationProcessResponse>> getBloodCheckingList() {
        List<DonationProcessResponse> bloodCheckingList = donationProcessService.getCompletedDonationProcess();

        return new BaseReponse<>(
                HttpStatus.OK.value(),
                "Get blood checking list successfully",
                bloodCheckingList
        );
    }

//    @PutMapping("/update-process-is-passed/{donationProcessId}")
//    public BaseReponse<DonationProcessResponse> updateProcessIsPassed(@PathVariable String donationProcessId,
//                                                                      @RequestBody BloodVolumeRequest bloodCheckingRequest) {
//        DonationProcessResponse donationProcessResponse = donationProcessService.updateProcessIsPassed(donationProcessId);
//        return new BaseReponse<>(HttpStatus.OK.value(), "Update process is passed successfully", donationProcessResponse);
//    }
}