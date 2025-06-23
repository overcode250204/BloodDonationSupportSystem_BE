package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonationRegistrationRepository extends JpaRepository<DonationRegistrationEntity, UUID> {
    Optional<DonationRegistrationEntity> findByDonationRegistrationId(UUID id);

    @Query("""
    SELECT new com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO(
        dr.donationRegistrationId,
        dr.status,
        bds.addressHospital,
        bds.startTime,
        bds.endTime,
        COALESCE(dp.volumeMl, 0),
        bds.donationDate,
        dr.registrationDate
    )
    FROM donation_registration dr
    JOIN dr.bloodDonationSchedule bds
    LEFT JOIN dr.donationProcess dp
    WHERE dr.donor.userId = :donorId
    ORDER BY dr.registrationDate DESC
""")
    List<DonorDonationInfoDTO> findAllDonationHistoryWithVolume(@Param("donorId") UUID donorId);

    @Query("""
    SELECT new com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO(
        dr.donationRegistrationId,
        dr.status,
        bds.addressHospital,
        bds.startTime,
        bds.endTime,
        COALESCE(dp.volumeMl, 0),
        bds.donationDate,
        dr.registrationDate
    )
    FROM donation_registration dr
    JOIN dr.bloodDonationSchedule bds
    LEFT JOIN dr.donationProcess dp
    WHERE dr.donationRegistrationId = :registrationId
""")
    DonorDonationInfoDTO findDonationHistoryById(@Param("registrationId") UUID registrationId);
}