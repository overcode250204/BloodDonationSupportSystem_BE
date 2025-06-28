package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.DonationEmergencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DonationEmergencyRepositoryMid extends JpaRepository<DonationEmergencyEntity, UUID> {
    boolean existsByDonationRegistration_DonationRegistrationId(UUID donationRegistrationId);
}
