package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO;
import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationProcessRepository extends JpaRepository<DonationProcessEntity, UUID> {
    boolean existsByDonationRegistrationProcess_DonationRegistrationId(UUID donationRegistrationId);
}
