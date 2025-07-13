package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.EmergencyDonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EmergencyDonationRepository extends JpaRepository<EmergencyDonationEntity, UUID> {
    Optional<EmergencyDonationEntity> findByDonationRegistration_DonationRegistrationId(UUID donationRegistrationId);

}
