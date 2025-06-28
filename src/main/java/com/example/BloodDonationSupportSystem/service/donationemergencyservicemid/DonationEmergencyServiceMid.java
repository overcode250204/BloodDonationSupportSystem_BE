package com.example.BloodDonationSupportSystem.service.donationemergencyservicemid;

import com.example.BloodDonationSupportSystem.repository.DonationEmergencyRepositoryMid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DonationEmergencyServiceMid {
    @Autowired
    private DonationEmergencyRepositoryMid donationEmergencyRepositoryMid;

    public boolean isInEmergency(UUID donationRegistrationId) {
        return donationEmergencyRepositoryMid.existsByDonationRegistration_DonationRegistrationId(donationRegistrationId);
    }
}
