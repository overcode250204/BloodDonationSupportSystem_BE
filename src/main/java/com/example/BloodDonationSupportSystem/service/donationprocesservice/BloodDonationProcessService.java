package com.example.BloodDonationSupportSystem.service.donationprocesservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonationProcessResponse;
import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.BloodDonationProcessRepository;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BloodDonationProcessService {

    @Autowired
    private BloodDonationProcessRepository bloodDonationProcessRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public List<DonationProcessResponse> getCompletedDonationProcess() {
        List<DonationProcessEntity> donationProcessList = bloodDonationProcessRepository.getUncheckedDonatedProcessesList();


        return donationProcessList.stream()
                .map(entity -> new DonationProcessResponse(
                        entity.getDonationProcessId(),
                        entity.getBloodTest(),
                        entity.getVolumeMl(),
                        entity.getStatus(),
                        entity.getBloodInventory() != null ? entity.getBloodInventory().getBloodTypeId() : null,
                        entity.getDonationRegistrationProcess().getDonationRegistrationId()))
                .collect(Collectors.toList());
    }

    public DonationProcessResponse updateProcessIsPassed(UUID processId, String bloodTest, String bloodTypeId) {
        DonationProcessEntity donationProcess = bloodDonationProcessRepository.findById(processId)
                .orElseThrow(() -> new ResourceNotFoundException("Donation process not found with id: " + processId));

        donationProcess.setBloodTest(bloodTest);

        BloodInventory bloodInventory = bloodInventoryRepository.findById(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood inventory not found with id: " + bloodTypeId));

        donationProcess.setBloodInventory(bloodInventory);

        DonationProcessEntity updatedProcess = bloodDonationProcessRepository.save(donationProcess);


        return new DonationProcessResponse(
                updatedProcess.getDonationProcessId(),
                updatedProcess.getBloodTest(),
                updatedProcess.getVolumeMl(),
                updatedProcess.getStatus(),
                updatedProcess.getBloodInventory().getBloodTypeId(),
                updatedProcess.getDonationRegistrationProcess().getDonationRegistrationId()
        );
    }
}
