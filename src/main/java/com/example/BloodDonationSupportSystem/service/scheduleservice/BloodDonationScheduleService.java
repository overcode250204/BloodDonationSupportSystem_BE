package com.example.BloodDonationSupportSystem.service.scheduleservice;

import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.BloodDonationScheduleDTO;
import com.example.BloodDonationSupportSystem.dto.donationregistrationDTO.StatByDateDTO;
import com.example.BloodDonationSupportSystem.entity.BloodDonationScheduleEntity;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.BloodDonationScheduleRepository;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
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


    public List<StatByDateDTO> getStatByDate() {
        LocalDate today = LocalDate.now();
        List<StatByDateDTO> result = new ArrayList<>();

        for (int i = 0; i <= 90; i++) {
            LocalDate date = today.plusDays(i);
            Long count = donationRegistrationRepository.countByDateBetweenStartAndEndDate(date, "CHƯA HIẾN");
            result.add(new StatByDateDTO(date, count));
        }
        return result;
    }


    public String createSchedule(BloodDonationScheduleDTO dto, UUID staffId) {

        BloodDonationScheduleEntity schedule = new BloodDonationScheduleEntity();
        schedule.setEditedByStaffId(userRepository.findById(staffId).orElseThrow(()-> new ResourceNotFoundException("Staff not found")));
        schedule.setDonationDate(dto.getDonationDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setAddressHospital(dto.getAddressHospital());
        schedule.setAmountRegistration(dto.getAmountRegistration());


        BloodDonationScheduleEntity saved = bloodDonationScheduleRepository.save(schedule);

        return assignRegistrationsToSchedule(saved);
    }


    public String assignRegistrationsToSchedule(BloodDonationScheduleEntity schedule) {
        List<DonationRegistrationEntity> eligibleRegistrations = donationRegistrationRepository.findEligibleRegistrations(schedule.getDonationDate());

        int max = schedule.getAmountRegistration();

        List<DonationRegistrationEntity> selectedRegistration = eligibleRegistrations.stream().limit(max).toList();

        for (DonationRegistrationEntity registration : selectedRegistration) {
            registration.setBloodDonationSchedule(schedule);
        }

        donationRegistrationRepository.saveAll(selectedRegistration);

        return "Successfully assigned " + selectedRegistration.size() + " registrations to the schedule";
    }




}
