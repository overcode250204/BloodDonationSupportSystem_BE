package com.example.BloodDonationSupportSystem.service.donationregistrationservice;

import com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.DonationRegistrationDTO;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.BloodDonationScheduleRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class DonationRegistrationService {
    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodDonationScheduleRepository bloodDonationScheduleRepository;

    public void updateCancelStatus(UUID registrationId, String status) {

        if (!"HỦY".equalsIgnoreCase(status)) {
            throw new BadRequestException("Invalid status.");
        }

        DonationRegistrationEntity registration = donationRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found."));

        registration.setStatus("HỦY");
        donationRegistrationRepository.save(registration);
    }

    public DonationRegistrationDTO create(DonationRegistrationDTO dto) {
        UserEntity donor = userRepository.findByUserId(dto.getDonorId()).orElseThrow(()-> new ResourceNotFoundException("Donor Not Found"));

        List<DonationRegistrationEntity> uncompleted = donationRegistrationRepository.findUncompletedRegistrations(donor.getUserId(), "CHƯA HIẾN");
        if (!uncompleted.isEmpty()) {
            throw new BadRequestException("You already have a pending registration!");
        }

        DonationRegistrationEntity latestRegistration = donationRegistrationRepository.findLatestRegistrationByDonor(donor.getUserId()).stream().findFirst().orElse(null);
        if (latestRegistration != null) {
            LocalDate today = LocalDate.now();
            LocalDate lastRegistrationDate  = latestRegistration.getDateCompleteDonation();
            if (lastRegistrationDate != null && ChronoUnit.DAYS.between(lastRegistrationDate, today) < 90) {
                throw new BadRequestException("You have donated within the last 90 days. Please wait a little longer !!!");
            }
        }

        DonationRegistrationEntity entity = new DonationRegistrationEntity();
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        entity.setDonor(userRepository.findById(dto.getDonorId())
                .orElseThrow(() -> new RuntimeException("Donor not found")));


        return mapToDTO(donationRegistrationRepository.save(entity));
    }

    public DonationRegistrationDTO update(UUID id, DonationRegistrationDTO dto) {
        DonationRegistrationEntity entity = donationRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DonationRegistration not found"));

        entity.setDateCompleteDonation(dto.getCompleteDonationDate());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        if (dto.getScreenedByStaffId() != null)
            entity.setScreenedByStaff(userRepository.findById(dto.getScreenedByStaffId()).orElse(null));

        if (dto.getBloodDonationScheduleId() != null)
            entity.setBloodDonationSchedule(bloodDonationScheduleRepository.findById(dto.getBloodDonationScheduleId()).orElse(null));

        return mapToDTO(donationRegistrationRepository.save(entity));
    }

    public void delete(UUID id) {
        try {
            DonationRegistrationEntity existing = donationRegistrationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("DonationRegistration not found with id: " + id));
            donationRegistrationRepository.delete(existing);
        } catch (Exception e) {
            throw new BadRequestException("DonationRegistration not found with id: " + id);
        }

    }


    public DonationRegistrationDTO getById(UUID id) {
        DonationRegistrationEntity existing = donationRegistrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonationRegistration not found with id: " + id));
        return mapToDTO(existing);
    }

    public List<DonationRegistrationDTO> getAll() {
        List<DonationRegistrationDTO> dtos;
        try {
            dtos = donationRegistrationRepository.findAll().stream().map(donationRegistrationEntity -> mapToDTO(donationRegistrationEntity)).toList();
        } catch (Exception e) {
            throw new ResourceNotFoundException("DonationRegistrations not found");
        }
        return dtos;
    }

//    @Transactional
//    public void cancelOtherRegistrationsOfDonor(UUID donorId, UUID keepId) {
//        donationRegistrationRepository.cancelOtherPendingRegistrations(donorId, keepId);
//    }


    private DonationRegistrationDTO mapToDTO(DonationRegistrationEntity entity) {
        DonationRegistrationDTO dto = new DonationRegistrationDTO();
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setCompleteDonationDate(entity.getDateCompleteDonation());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        if (entity.getScreenedByStaff() != null)
            dto.setScreenedByStaffId(entity.getScreenedByStaff().getUserId());
        if (entity.getDonor() != null)
            dto.setDonorId(entity.getDonor().getUserId());
        if (entity.getBloodDonationSchedule() != null)
            dto.setBloodDonationScheduleId(entity.getBloodDonationSchedule().getBloodDonationScheduleId());
        return dto;
    }


}
