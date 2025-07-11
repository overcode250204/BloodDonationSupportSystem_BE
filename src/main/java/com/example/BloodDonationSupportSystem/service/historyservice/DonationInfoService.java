package com.example.BloodDonationSupportSystem.service.historyservice;

import com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO;
import com.example.BloodDonationSupportSystem.entity.DonationHistoryEntity;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.DonationHistoryRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class DonationInfoService {

    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public List<DonorDonationInfoDTO> getDonationInfo() {
        try {
            UserDetails currentUser = AuthUtils.getCurrentUser();
            UUID userId;
            try {
                userId = UUID.fromString(currentUser.getUsername());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid UUID format for user ID: " + currentUser.getUsername());
            }
            List<DonorDonationInfoDTO> donationInfo = donationRegistrationRepository.findAllDonationHistoryWithVolume(userId);
            if (donationInfo.isEmpty()) {
                throw new ResourceNotFoundException("No donation information found for user ID: " + userId);
            }
            return donationInfo;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting current user donation info");
        }
    }

    public void cancelDonation(UUID donationRegistrationId) {
        try {
            DonationRegistrationEntity registration = donationRegistrationRepository.findByDonationRegistrationId(donationRegistrationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Donation registration not found with ID: " + donationRegistrationId));

            if (!"CHƯA HIẾN".equalsIgnoreCase(registration.getStatus())) {
                throw new BadRequestException("Can only be canceled when the status is 'CHƯA HIẾN'");
            }

            registration.setStatus("HỦY");
            donationRegistrationRepository.save(registration);
        } catch (Exception e) {
            throw new RuntimeException("Error while canceling donation registration", e);
        }
    }

    public DonorDonationInfoDTO getDonationInfoById(UUID registrationId) {
        try {
            DonorDonationInfoDTO donationInfo = donationRegistrationRepository.findDonationHistoryById(registrationId);
            if (donationInfo == null) {
                throw new ResourceNotFoundException("Donation registration not found with ID: " + registrationId);
            }
            return donationInfo;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting donation info by ID", e);
        }
    }

    public void saveDonationHistory(DonationRegistrationEntity registration) {

        DonationHistoryEntity history = new DonationHistoryEntity();
        history.setRegistrationDate(registration.getRegistrationDate());
        history.setAddressHospital(registration.getBloodDonationSchedule().getAddressHospital());
        history.setDonationRegistration(registration);
        history.setDonorHistory(registration.getDonor());

        donationHistoryRepository.save(history);
    }

}
