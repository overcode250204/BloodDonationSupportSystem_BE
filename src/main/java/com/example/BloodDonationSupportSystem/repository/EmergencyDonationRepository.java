package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.EmergencyDonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmergencyDonationRepository extends JpaRepository<EmergencyDonationEntity, UUID> {

}
