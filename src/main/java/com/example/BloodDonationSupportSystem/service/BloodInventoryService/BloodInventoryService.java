package com.example.BloodDonationSupportSystem.service.BloodInventoryService;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.entity.BloodBag;
import com.example.BloodDonationSupportSystem.repository.BloodBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BloodBagService {
    @Autowired
    private BloodBagRepository bloodBagRepository;

    public BloodBagResponse createBloodBag(BloodBagRequest bloodBagRequest) {

        BloodBag bloodBag = new BloodBag();
        bloodBag.setBloodType(bloodBagRequest.getBloodType().getValue());// cái này co the null nen fix lai
        System.out.println("Creating blood bag with type: " + bloodBagRequest.getBloodType());
        bloodBag.setVolume(bloodBagRequest.getVolume());
        bloodBag.setStatus(bloodBagRequest.getStatus());
        bloodBag.setCreateAtDate(bloodBagRequest.getCreateAtDate());
        if (bloodBagRequest.getBloodDonationHistoryId() == null) {
            bloodBag.setBloodDonationHistory(UUID.randomUUID()); // Fake nếu null
        } else {
            bloodBag.setBloodDonationHistory(bloodBagRequest.getBloodDonationHistoryId()); // Đúng rồi!
        }
        // Save the blood bag to the database

         bloodBagRepository.save(bloodBag);
        System.out.println("Saving blood bag with ID: " + bloodBag.getBloodBagId());
         return  new BloodBagResponse(bloodBag.getBloodBagId(),
                 bloodBag.getBloodType(),
                 bloodBag.getVolume(),
                 bloodBag.getStatus(),
                 bloodBag.getCreateAtDate(),
                 bloodBag.getBloodDonationHistory());

    }
}
