package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodVolumeRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;

import com.example.BloodDonationSupportSystem.service.bloodinventoryservice.BloodInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/staff")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodBagService;


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
}