package com.example.BloodDonationSupportSystem.service.blooddonationscheduleservice;

import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.request.BloodDonationScheduleRequest;
import com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO.response.BloodDonationScheduleResponse;
import com.example.BloodDonationSupportSystem.entity.BloodDonationScheduleEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.BloodDonationScheduleRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BloodDonationScheduleService {
    @Autowired
    private BloodDonationScheduleRepository bloodDonationScheduleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BloodDonationScheduleResponse> getAllBloodDonationSchedule() {
       return bloodDonationScheduleRepository.findAll().stream().map(x -> this.mapToResponse(x)).toList();
    }

    public BloodDonationScheduleResponse getBloodDonationSchedule(UUID id) {
        return bloodDonationScheduleRepository.findById(id).map(x -> this.mapToResponse(x)).orElseThrow(() -> new ResourceNotFoundException("Schedule not found") );
    }

    public BloodDonationScheduleResponse createBloodDonationSchedule(BloodDonationScheduleRequest request) {
        Date today = new Date();

        if (request.getDonationDate().before(today)) {
            throw new BadRequestException("Ngày hiến máu không được ở quá khứ");
        }
        BloodDonationScheduleEntity e = new BloodDonationScheduleEntity();
        e.setAddressHospital(request.getAddressHospital());
        e.setDonationDate(request.getDonationDate());
        e.setStartTime(request.getStartTime());
        e.setEndTime(request.getEndTime());
        e.setAmountRegistration(request.getAmountRegistration());
        e.setEditedByStaffId(userRepository.findById(request.getEditedByStaffId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found")));
        bloodDonationScheduleRepository.save(e);
        return mapToResponse(e);
    }

    public BloodDonationScheduleResponse updateBloodDonationSchedule(UUID id,BloodDonationScheduleRequest request) {
        UserEntity staff = userRepository.findById(request.getEditedByStaffId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
        BloodDonationScheduleEntity e = bloodDonationScheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        e.setAddressHospital(request.getAddressHospital());
        e.setDonationDate(request.getDonationDate());
        e.setStartTime(request.getStartTime());
        e.setEndTime(request.getEndTime());
        e.setAmountRegistration(request.getAmountRegistration());
        e.setEditedByStaffId(staff);
        bloodDonationScheduleRepository.save(e);
        return mapToResponse(e);
    }

    public void deleteBloodDonationSchedule(UUID id) {
        BloodDonationScheduleEntity e = bloodDonationScheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        bloodDonationScheduleRepository.delete(e);
    }

    private BloodDonationScheduleResponse mapToResponse (BloodDonationScheduleEntity entity) {
        BloodDonationScheduleResponse response = new BloodDonationScheduleResponse();
        response.setBloodDonationScheduleId(entity.getBloodDonationScheduleId());
        response.setAmountRegistration(entity.getAmountRegistration());
        response.setAddressHospital(entity.getAddressHospital());
        response.setDonationDate(entity.getDonationDate());
        response.setStartTime(entity.getStartTime());
        response.setEndTime(entity.getEndTime());
        return  response;
    }
}
