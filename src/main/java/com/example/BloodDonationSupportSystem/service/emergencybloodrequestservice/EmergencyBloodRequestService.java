package com.example.BloodDonationSupportSystem.service.emergencybloodrequestservice;

import com.example.BloodDonationSupportSystem.dto.emergencybloodrequestDTO.EmergencyBloodRequestDTO;
import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import com.example.BloodDonationSupportSystem.repository.EmergencyBloodRequestRepository;
import com.example.BloodDonationSupportSystem.repository.EmergencyDonationRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class EmergencyBloodRequestService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmergencyBloodRequestRepository emergencyBloodRequestRepository;

    @Autowired
    private DonationRegistrationRepository registrationRepository;

    @Autowired
    private EmergencyDonationRepository emergencyDonationRepository;


    public EmergencyBloodRequestDTO createEmergencyRequest(EmergencyBloodRequestDTO dto) {
        UserEntity staff = userRepository.findByUserId(dto.getRegisteredByStaff()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        EmergencyBloodRequestEntity entity = new EmergencyBloodRequestEntity();
        entity.setPatientName(dto.getPatientName());
        entity.setPatientRelatives(dto.getPatientRelatives());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setLocationOfPatient(dto.getLocationOfPatient());
        entity.setBloodType(dto.getBloodType());
        entity.setVolumeMl(dto.getVolumeMl());
        entity.setRegistrationDate(LocalDate.now());
        entity.setFulfill(false);
        entity.setLevelOfUrgency(dto.getLevelOfUrgency());
        entity.setNote(dto.getNote());
        entity.setRegisteredByStaff(staff);

        EmergencyBloodRequestEntity emergencyBloodRequest = emergencyBloodRequestRepository.save(entity);

        dto.setEmergencyBloodRequestId(emergencyBloodRequest.getEmergencyBloodRequestId());
        messagingTemplate.convertAndSend("/emergency/emergency-requests", dto);

        return dto;
    }

    @Transactional
    public void updateFulfilledEmergencyRequests() {
        emergencyBloodRequestRepository.markFulfilledRequests("ĐÃ HIẾN", "ĐÃ HIẾN", "ĐÃ ĐẠT");
    }
    public List<EmergencyBloodRequestDTO> getEmergencyCasesWithSortedLevelOfUrgency() {
        List<EmergencyBloodRequestEntity> emergencyRequests = emergencyBloodRequestRepository.getAllIsFulfillEmergencyBloodRequests();
        emergencyRequests.sort(Comparator.comparingInt(e -> {
            return switch (e.getLevelOfUrgency()) {
                case "CỰC KÌ KHẨN CẤP" -> 1;
                case "RẤT KHẨN CẤP" -> 2;
                case "KHẨN CẤP" -> 3;
                default -> 4;
            };
        }));


        return emergencyRequests.stream().map(e -> mapptoDTO(e, e.getRegisteredByStaff())).toList();


    }

    private EmergencyBloodRequestDTO mapptoDTO(EmergencyBloodRequestEntity entity, UserEntity staff) {
        EmergencyBloodRequestDTO dto = null;
        if (entity != null) {
            dto = new EmergencyBloodRequestDTO();
            dto.setEmergencyBloodRequestId(entity.getEmergencyBloodRequestId());
            dto.setPatientName(entity.getPatientName());
            dto.setPatientRelatives(entity.getPatientRelatives());
            dto.setPhoneNumber(entity.getPhoneNumber());
            dto.setLocationOfPatient(entity.getLocationOfPatient());
            dto.setBloodType(entity.getBloodType());
            dto.setVolumeMl(entity.getVolumeMl());
            dto.setLevelOfUrgency(entity.getLevelOfUrgency());
            dto.setRegistrationDate(entity.getRegistrationDate());
            dto.setNote(entity.getNote());
            dto.setRegisteredByStaff(staff.getUserId());
            dto.setStaffName(entity.getRegisteredByStaff().getFullName());
        }

        return dto;
    }


}
