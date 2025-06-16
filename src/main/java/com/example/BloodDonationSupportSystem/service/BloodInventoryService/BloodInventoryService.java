package com.example.BloodDonationSupportSystem.service.BloodInventoryService;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.BloodInventoryResponse;

import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import com.example.BloodDonationSupportSystem.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;


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

    public  BloodInventoryResponse updateBloodVolume(String bloodTypeId, int volumeToAdd) {
        BloodInventory bloodInventory = bloodInventoryRepository.findById(bloodTypeId)
                .orElseThrow(() -> new RuntimeException("Blood type not found with id: " + bloodTypeId));

        bloodInventory.setTotalVolumeMl(bloodInventory.getTotalVolumeMl() + volumeToAdd);
        bloodInventoryRepository.save(bloodInventory);

        return new BloodInventoryResponse(
                bloodInventory.getBloodTypeId(),
                bloodInventory.getTotalVolumeMl());
    }

}
