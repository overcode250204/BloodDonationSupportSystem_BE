package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.DonationEmergencyEntity;
import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DonationEmergencyRepository extends JpaRepository<DonationEmergencyEntity, UUID> {
    boolean existsByDonationRegistrationDonationRegistrationId(UUID donationRegistrationId);

}
