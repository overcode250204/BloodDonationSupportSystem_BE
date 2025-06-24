package com.example.BloodDonationSupportSystem.service.scheduleservice;

import com.example.BloodDonationSupportSystem.dto.scheduleDTO.ScheduleDTO;
import com.example.BloodDonationSupportSystem.entity.BloodDonationScheduleEntity;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.BloodDonationScheduleRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import com.example.BloodDonationSupportSystem.service.donationregistrationservice.DonationRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class BloodDonationScheduleService {

    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodDonationScheduleRepository bloodDonationScheduleRepository;

    @Autowired
    private DonationRegistrationService donationRegistrationService;
    public BloodDonationScheduleEntity createSchedule(ScheduleDTO dto, UUID staffId) {
        BloodDonationScheduleEntity schedule = new BloodDonationScheduleEntity();
        schedule.setDonationDate(dto.getDonationDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setAddressHospital(dto.getAddressHospital());
        schedule.setAmountRegistration(dto.getAmountRegistration());
        schedule.setEditedByStaffId(userRepository.findById(staffId).orElseThrow(()-> new ResourceNotFoundException("Staff not found")));

        BloodDonationScheduleEntity saved = bloodDonationScheduleRepository.save(schedule);

        assignRegistrationsToSchedule(saved);

        return saved;
    }


    public String assignRegistrationsToSchedule(BloodDonationScheduleEntity schedule) {
        List<DonationRegistrationEntity> eligibleRegistrations = donationRegistrationRepository.findEligibleRegistrations(schedule.getDonationDate());

        int max = schedule.getAmountRegistration();

        List<DonationRegistrationEntity> selectedRegistration = eligibleRegistrations.stream().limit(max).toList();

        for (DonationRegistrationEntity registration : selectedRegistration) {
            registration.setBloodDonationSchedule(schedule);
//            donationRegistrationService.cancelOtherRegistrationsOfDonor(registration.getDonor().getUserId(), registration.getDonationRegistrationId());
        }

        donationRegistrationRepository.saveAll(selectedRegistration);

        return "Successfully assigned " + selectedRegistration.size() + " registrations to the schedule";
    }




}
