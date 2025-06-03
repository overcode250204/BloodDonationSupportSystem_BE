package com.example.BloodDonationSupportSystem.service.processmanagement;


import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToCollectingRequest;
import com.example.BloodDonationSupportSystem.dto.processmanagement.UpdateToScreeningRequest;
import com.example.BloodDonationSupportSystem.entity.BloodDonationProcess;
import com.example.BloodDonationSupportSystem.entity.MemberScreening;

import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.HealthStatusEnum;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.MemberScreeningStatus;
import com.example.BloodDonationSupportSystem.repository.processmanagement.BloodDonationProcessRepository;
import com.example.BloodDonationSupportSystem.repository.processmanagement.MemberScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberScreeningServiceImpl implements MemberScreeningService {

    @Autowired
    private MemberScreeningRepository memberScreeningRepository;

    @Autowired
    private BloodDonationProcessRepository bloodDonationProcessRepository;


    @Override
    public List<MemberScreening> findByStatus(MemberScreeningStatus status) {
        return memberScreeningRepository.findByStatus(status);
    }

    @Override
    public String updateToScreening(UpdateToScreeningRequest req) {
        Optional<MemberScreening> memberScreening = memberScreeningRepository.findById(req.getMember_screening_id());
        if (memberScreening.isPresent()) {
            MemberScreening screening = memberScreening.get();
            screening.setStatus(MemberScreeningStatus.SCREENING);
            memberScreeningRepository.save(screening);
        } else {
            throw new RuntimeException("Screening not found with ID: " + req.getMember_screening_id());
        }
        return "PENDING status updated to SCREENING";
    }

    @Override
    public String updateToCollecting(UpdateToCollectingRequest req) {
        if (req.getMember_screening_id() == null || req.getHealth_status() == null) {
            throw new IllegalArgumentException("Member screening ID and health status must not be null");
        }
        String message = "";
        Optional<MemberScreening> optionalMemberScreening = memberScreeningRepository.findById(req.getMember_screening_id());
        if (optionalMemberScreening.isPresent()) {
            MemberScreening memberScreening = optionalMemberScreening.get();

            if (memberScreening.getStatus() == MemberScreeningStatus.SCREENING) {
                memberScreening.setHealth_status(req.getHealth_status());
            } else {
                throw new RuntimeException("Screening is not in 'SCREENING' status");
            }

            if (req.getHealth_status() == HealthStatusEnum.PASSED && memberScreening.getStatus()== MemberScreeningStatus.SCREENING) {
                if (!bloodDonationProcessRepository.findById(req.getMember_screening_id()).isPresent()) {
                    BloodDonationProcess bloodDonationProcess = new BloodDonationProcess();
                    bloodDonationProcess.setDonation_process_id(req.getMember_screening_id());
                    bloodDonationProcess.setScreening_id(memberScreening);
                    bloodDonationProcess.setStatus(BloodDonationProcessStatus.COLLECTING);
                    bloodDonationProcess.setVolumeBloodCollected(0);
                    bloodDonationProcessRepository.save(bloodDonationProcess);
                    message = "SCREENING status updated to COLECTING";
                } else {
                    throw new RuntimeException("Blood Donation Process already exists");
                }
            }

            if (req.getHealth_status() == HealthStatusEnum.FAILED) {
                memberScreening.setStatus(MemberScreeningStatus.FAIL);
                message = "SCREENING status updated to FAIL";
            }

            memberScreeningRepository.save(memberScreening);
        } else {
            throw new RuntimeException("Screening not found with ID: " + req.getMember_screening_id());
        }
        return message;
    }
}
