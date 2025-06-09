package com.example.BloodDonationSupportSystem.service.BloodInventoryService;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.entity.BloodBag;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public BloodBagResponse createBloodBag(BloodBagRequest bloodBagRequest) {
//        DonationRegisteration donationRegister = DonationRepository.findById(bloodBagRequest.getDonationId()).orElseThrow(
//                ()-> new RuntimeException("Donation registration not found with ID: " + bloodBagRequest.getDonationId()));
        BloodBag bloodBag = new BloodBag();
        bloodBag.setBloodType(bloodBagRequest.getBloodType());
        bloodBag.setVolume(bloodBagRequest.getVolume());
        bloodBag.setAmountBag(bloodBagRequest.getAmount_bag());
        bloodBag.setCreatedAt(bloodBagRequest.getCreateAt());
        bloodBag.setExpiredDate(bloodBagRequest.getExpiredDate());
        bloodBag.setStatus(bloodBagRequest.getStatus());
      //  bloodBag.setDonationRegisteration(donationRegister);


        bloodInventoryRepository.save(bloodBag);
         return  new BloodBagResponse(
                 bloodBag.getBloodBagId(),
                 bloodBag.getBloodType(),
                 bloodBag.getVolume(),
                 bloodBag.getAmountBag(),
                 bloodBag.getCreatedAt(),
                 bloodBag.getExpiredDate(),
                 bloodBag.getStatus());
              //   bloodBag.getDonationRegisteration().getDonation_registration_id());

    }
}
