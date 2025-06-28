package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodDonationRegistrionRepository extends JpaRepository<DonationRegistrationEntity, UUID> {
}
