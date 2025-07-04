package com.example.BloodDonationSupportSystem.service.bloodinventoryservice;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.EmergencyBloodEntityResponse;
import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import com.example.BloodDonationSupportSystem.repository.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private DonationEmergencyRepository donationEmergencyRepository;

    @Autowired
    private  DonationEmergencyRequestRepository donationEmergencyRequestRepository;
    @Autowired
    private BloodDonationRegistrionRepository bloodDonationRegistrionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonationProcessRepository donationProcessRepository;
    public List<BloodInventoryResponse> getBloodBagList(){
        List<BloodInventory> bloodTotalList = bloodInventoryRepository.findAll();

        return  bloodTotalList.stream()
                .map(allBloodBagInventory -> new BloodInventoryResponse(
                        allBloodBagInventory.getBloodTypeId(),
                        allBloodBagInventory.getTotalVolumeMl())).toList();

    }

    public BloodInventoryResponse getBloodBagById(String bloodTypeId){
        BloodInventory bloodInventory = bloodInventoryRepository.findById(bloodTypeId)
                .orElseThrow(() -> new RuntimeException("Blood type not found with id: " + bloodTypeId));
        return new BloodInventoryResponse(
                bloodInventory.getBloodTypeId(),
                bloodInventory.getTotalVolumeMl());
    }

    public BaseReponse<?> updateBloodVolume(String bloodTypeId, UUID donationRegisId, UUID processId, int volumeToAdd) {
        BloodInventory bloodInventory = bloodInventoryRepository.findById(bloodTypeId)
                .orElseThrow(() -> new RuntimeException("Blood type not found with id: " + bloodTypeId));


        boolean checkRegistrationId = donationEmergencyRepository.existsByDonationRegistrationDonationRegistrationId(donationRegisId);
        if (checkRegistrationId) {
            Optional<EmergencyBloodRequestEntity> optionalEmergencyRequest =
                    donationEmergencyRequestRepository.findEmergencyBloodRequestEntityByDonationRegistrationId(donationRegisId);
            if (optionalEmergencyRequest.isPresent()) {
                EmergencyBloodRequestEntity emergencyRequest = optionalEmergencyRequest.get();
                boolean checkBloodType = emergencyRequest.getBloodType().equals(bloodTypeId);
                if(checkBloodType){
                    if(emergencyRequest.getVolumeMl() > volumeToAdd){
                        emergencyRequest.setVolumeMl(emergencyRequest.getVolumeMl() - volumeToAdd);
                        donationEmergencyRequestRepository.save(emergencyRequest);
                        return new BaseReponse<>(200, "Update blood volume successfully. Emergency request volume updated.", new EmergencyBloodEntityResponse(
                                emergencyRequest.getEmergencyBloodRequestId(),
                                emergencyRequest.getPatientName(),
                                emergencyRequest.getLocationOfPatient(),
                                emergencyRequest.getBloodType(),
                                emergencyRequest.getVolumeMl(),
                                emergencyRequest.getLevelOfUrgency()));
                    }
                    if (emergencyRequest.getVolumeMl() <= volumeToAdd  ) {
                        emergencyRequest.setVolumeMl(0);
                        donationEmergencyRequestRepository.save(emergencyRequest);
                        return new BaseReponse<>(200, "Update blood volume successfully. Emergency request volume updated and completed for emergency.", new EmergencyBloodEntityResponse(
                                emergencyRequest.getEmergencyBloodRequestId(),
                                emergencyRequest.getPatientName(),
                                emergencyRequest.getLocationOfPatient(),
                                emergencyRequest.getBloodType(),
                                emergencyRequest.getVolumeMl(),
                                emergencyRequest.getLevelOfUrgency()));
                    }
                }
            }
        }

        int updatedVolume = bloodInventory.getTotalVolumeMl() + volumeToAdd;
        bloodInventory.setTotalVolumeMl(updatedVolume);
        bloodInventoryRepository.save(bloodInventory);


        boolean checkUpdateUserBloodType = updateUserBloodType(processId);
        String message = checkUpdateUserBloodType
                ? "User has been updated with blood type from process"
                : "User already has a blood type set, no update needed";

        BloodInventoryResponse updatedBloodInventory = new BloodInventoryResponse(
                bloodInventory.getBloodTypeId(),
                bloodInventory.getTotalVolumeMl());
        return new BaseReponse<>(200, "Update blood volume successfully. " + message, updatedBloodInventory);
    }


    public boolean updateUserBloodType(UUID processId) {
        Optional<UserEntity> optionalUser = userRepository.findUserByProcessId(processId);
        boolean checkUser =false;
        if (!optionalUser.isPresent()) {
            System.err.println("User not found for process ID: " + processId);
            return checkUser;
        }
        DonationProcessEntity process = donationProcessRepository.findById(processId)
                .orElseThrow(() -> new RuntimeException("Donation Process not found with id: " + processId));
        UserEntity user = optionalUser.get();

        if (process.getBloodInventory() == null) {
            System.err.println("Process has no blood inventory information");
            checkUser=false;
            return checkUser;
        }

        String bloodType = process.getBloodInventory().getBloodTypeId();
        if (bloodType == null) {
            System.err.println("No blood type available in process");
            checkUser=false;
            return checkUser;
        }
        //chỗ này check lại xem nhen vd update r thì ko update loại máu mới sẽ lỗi vd nhân test loại B trc đó A thì ko lưu lại nhé
        if (user.getBloodType() == null || user.getBloodType().isBlank()) {
            user.setBloodType(bloodType);
            userRepository.save(user);
            checkUser=true;
            return checkUser;
        }
        System.err.println("User already has blood type: " + user.getBloodType());
        checkUser=false;
        return checkUser;
    }
}



