package com.example.BloodDonationSupportSystem.service.donationprocesservice;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonationProcessResponse;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.repository.BloodDonationProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BloodDonationProcessService {

    @Autowired
    private BloodDonationProcessRepository bloodDonationProcessRepository;

    public List<DonationProcessResponse> getCompletedDonationProcess() {
        List<DonationProcessEntity> donationProcessList = bloodDonationProcessRepository.getUncheckedDonatedProcessesList();
        //id,bloodTest,volumeMl,status,bloodTypeId,typeDonation,donationRegisId
        return donationProcessList.stream()
                .map(entity -> new DonationProcessResponse(
                        entity.getDonationProcessId(),
                        entity.getBloodTest(),
                        entity.getVolumeMl(),
                        entity.getStatus(),
                        entity.getBloodInventory().getBloodTypeId(),
                        entity.getTypeDonation(),
                        entity.getDonationRegistrationProcess().getDonationRegistrationId()))
                .collect(Collectors.toList());
    }

//    public DonationProcessResponse updateProcessIsPassed(UUID processId,String is_passed) {
//        DonationProcessEntity donationProcess = (DonationProcessEntity) bloodDonationProcessRepository.findById(processId)
//                .orElseThrow(() -> new RuntimeException("Donation process not found with id: " + processId));
//
//        donationProcess.setIsPassed("");
//        return null;
//    }
}
