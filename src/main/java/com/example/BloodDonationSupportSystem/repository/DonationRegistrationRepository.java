package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonationRegistrationRepository extends JpaRepository<DonationRegistrationEntity, UUID> {
    Optional<DonationRegistrationEntity> findByDonationRegistrationId(UUID id);

    @Query("""
    SELECT COUNT(dr)
    FROM donation_registration dr
    WHERE dr.status = :donationRegistrationStatus AND :date BETWEEN dr.startDate AND dr.endDate
""")
    Long countByDateBetweenStartAndEndDate(@Param("date") LocalDate date, @Param("donationRegistrationStatus") String donationRegistrationStatus);




    @Query("""
    SELECT dr
    FROM donation_registration dr
    WHERE dr.status = :donationRegistrationStatus AND dr.bloodDonationSchedule IS NULL AND :donationDate BETWEEN dr.startDate AND dr.endDate
    ORDER BY dr.registrationDate ASC
""")
    List<DonationRegistrationEntity> findEligibleRegistrations(@Param("donationDate")LocalDate donationDate, @Param("donationRegistrationStatus") String donationRegistrationStatus);


    @Query("""
    SELECT dr
    FROM donation_registration dr
    WHERE dr.donor.userId = :donorId AND dr.status = :status
""")
    List<DonationRegistrationEntity> findUncompletedRegistrations(@Param("donorId") UUID donorId, @Param("status") String status);


    @Query("""
    SELECT dr
    FROM donation_registration dr
    WHERE dr.donor.userId = :donorId AND dr.dateCompleteDonation IS NOT NULL
    ORDER BY dr.dateCompleteDonation DESC
""")
    List<DonationRegistrationEntity> findLatestRegistrationByDonor(@Param("donorId") UUID donorId);

    @Query("""
    SELECT new com.example.BloodDonationSupportSystem.dto.donationhistoryDTO.DonorDonationInfoDTO(
        dr.donationRegistrationId,
        dr.status,
        bds.addressHospital,
        bds.startTime,
        bds.endTime,
        COALESCE(dp.volumeMl, 0),
        bds.donationDate,
        dr.registrationDate,
        bds.bloodDonationScheduleId,
        dr.startDate,
        dr.endDate,
        de.donationEmergencyId,
        de.assignedDate
    )
    FROM donation_registration dr
    LEFT JOIN dr.bloodDonationSchedule bds
    LEFT JOIN dr.donationProcess dp
    LEFT JOIN dr.donationEmergencies de
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
        dr.registrationDate,
        bds.bloodDonationScheduleId,
        dr.startDate,
        dr.endDate,
        de.donationEmergencyId,
        de.assignedDate
    )
    FROM donation_registration dr
    LEFT JOIN dr.bloodDonationSchedule bds
    LEFT JOIN dr.donationProcess dp
    LEFT JOIN dr.donationEmergencies de
    WHERE dr.donationRegistrationId = :registrationId
""")
    DonorDonationInfoDTO findDonationHistoryById(@Param("registrationId") UUID registrationId);
}