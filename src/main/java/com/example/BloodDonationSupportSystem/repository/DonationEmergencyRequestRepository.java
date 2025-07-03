package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DonationEmergencyRequestRepository extends JpaRepository<EmergencyBloodRequestEntity, UUID> {
}
