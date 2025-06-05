package com.example.BloodDonationSupportSystem.service.BloodInventoryService;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.BloodBagRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodBagResponse;
import com.example.BloodDonationSupportSystem.entity.BloodBag;
import com.example.BloodDonationSupportSystem.entity.DonationRegisterationFake;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import com.example.BloodDonationSupportSystem.repository.FakeDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private FakeDonationRepository fakeDonationRepository;

    public DonationRegisterationFake createFakeDonation(DonationRegisterationFake donationRegisterationFake) {
        return fakeDonationRepository.save(donationRegisterationFake);
    }

    public BloodBagResponse createBloodBag(BloodBagRequest bloodBagRequest) {
        DonationRegisterationFake donaFake = fakeDonationRepository.findById(bloodBagRequest.getDonationId()).orElseThrow(
                ()-> new RuntimeException("Donation registration not found with ID: " + bloodBagRequest.getDonationId()));
        BloodBag bloodBag = new BloodBag();
        bloodBag.setBloodType(bloodBagRequest.getBloodType());
        bloodBag.setAmountBag(bloodBagRequest.getAmount_bag());
        bloodBag.setVolume(bloodBagRequest.getVolume());
        bloodBag.setCreatedAt(bloodBagRequest.getCreateAt());
        bloodBag.setExpiredDate(bloodBagRequest.getExpiredDate());
        bloodBag.setStatus(bloodBagRequest.getStatusBloodBagEnum());
        bloodBag.setDonationRegisteration(donaFake);


        bloodInventoryRepository.save(bloodBag);
         return  new BloodBagResponse(
                 bloodBag.getBloodBagId(),
                 bloodBag.getBloodType(),
                 bloodBag.getAmountBag(),
                 bloodBag.getVolume(),
                 bloodBag.getCreatedAt(),
                 bloodBag.getExpiredDate(),
                 bloodBag.getStatus(),
                 bloodBag.getDonationRegisteration().getDonation_registration_id());

    }
}
