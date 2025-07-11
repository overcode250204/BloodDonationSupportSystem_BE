package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.DonationCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DonationCertificateRepository extends JpaRepository<DonationCertificateEntity, UUID> {
}
