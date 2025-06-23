package com.example.BloodDonationSupportSystem.repositories;

import com.example.BloodDonationSupportSystem.entities.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {

}
