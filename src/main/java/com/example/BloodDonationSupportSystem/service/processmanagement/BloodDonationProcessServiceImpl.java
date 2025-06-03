package com.example.BloodDonationSupportSystem.service.processmanagement;

import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCompletedRequest;
import com.example.BloodDonationSupportSystem.entity.BloodDonationHistory;
import com.example.BloodDonationSupportSystem.entity.BloodDonationProcess;

import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import com.example.BloodDonationSupportSystem.repository.processmanagement.BloodDonationHistoryRepository;
import com.example.BloodDonationSupportSystem.repository.processmanagement.BloodDonationProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BloodDonationProcessServiceImpl implements BloodDonationProcessService{

    @Autowired
    private BloodDonationProcessRepository bloodDonationProcessRepository;

    @Autowired
    private BloodDonationHistoryRepository bloodDonationHistoryRepository;

    @Override
    public List<BloodDonationProcess> findByStatus(BloodDonationProcessStatus status) {
        return bloodDonationProcessRepository.findByStatus(status);
    }

    @Override
    public String updateToCompleted(UpdateToCompletedRequest req) {
        if(req.getDonation_process_id() == null || req.getVolume_ml() == null) {
            throw new IllegalArgumentException("Donation process ID and volume must not be null");
        }
        Optional<BloodDonationProcess> optionalProcess = bloodDonationProcessRepository.findById(req.getDonation_process_id());
        if(optionalProcess.isPresent()) {
            BloodDonationProcess process = optionalProcess.get();

            if(process.getStatus() == BloodDonationProcessStatus.COLLECTING) {
                process.setVolumeBloodCollected(req.getVolume_ml());
                process.setStatus(BloodDonationProcessStatus.COMPLETED);
                bloodDonationProcessRepository.save(process);

                BloodDonationHistory history = new BloodDonationHistory();
                history.setBlood_donation_history_id(req.getDonation_process_id());
                history.setDonation_process_id(process);
                history.setDonation_date(LocalDateTime.now());
                history.setVolume_ml(req.getVolume_ml());
                bloodDonationHistoryRepository.save(history);

            } else {
                throw new RuntimeException("Blood Donation Process is not in 'COLLECTING' status");
            }


        } else {
            throw new RuntimeException("Blood Donation Process not found with ID: " + req.getDonation_process_id());
        }
        return "Blood Donation Process updated to COMPLETED successfully.";
    }
}
