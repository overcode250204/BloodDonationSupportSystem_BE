package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {

}
