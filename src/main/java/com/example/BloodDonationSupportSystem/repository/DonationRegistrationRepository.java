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
    @Query(value = "SELECT COUNT(*) FROM donation_registration dr " +
            "WHERE "+
            " YEAR(dr.registration_date) = :year " +
            "AND MONTH(dr.registration_date) = :month",
            nativeQuery = true)
    long countFilter(int year, int month);
    @Query(value = "SELECT COUNT(*) FROM donation_registration dr " +
            "WHERE "+
            "dr.status = N'ĐÃ HIẾN' AND" +
            " YEAR(dr.registration_date) = :year " +
            "AND MONTH(dr.registration_date) = :month ",
            nativeQuery = true)
    long countNumberSuccessDonationFilter(int year, int month);
    @Query(value = "SELECT COUNT(*) FROM donation_registration dr " +
            "WHERE "+
            "dr.status = N'HỦY' AND" +
            " YEAR(dr.registration_date) = :year " +
            "AND MONTH(dr.registration_date) = :month",
            nativeQuery = true)
    long countNumberFailureDonationFilter(int year, int month);
    @Query(value = "SELECT COUNT(*) FROM donation_registration dr " +
            "WHERE "+
            "dr.status = N'CHƯA HIẾN' AND " +
            " YEAR(dr.registration_date) = :year " +
            "AND MONTH(dr.registration_date) = :month",
            nativeQuery = true)
    long countNumberNotCompleteDonationFilter(int year, int month);
    @Query(value = "SELECT COUNT(*) FROM donation_registration dr " +
            "WHERE "+
            "dr.status is null AND " +
            " YEAR(dr.registration_date) = :year " +
            "AND MONTH(dr.registration_date) = :month ",
            nativeQuery = true)
    long countNumberNotAcceptedDonationFilter(int year, int month);
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
    WHERE dr.status = 'CHƯA HIẾN' AND dr.bloodDonationSchedule IS NULL AND :donationDate BETWEEN dr.startDate AND dr.endDate
    ORDER BY dr.registrationDate ASC
""")
    List<DonationRegistrationEntity> findEligibleRegistrations(@Param("donationDate")LocalDate donationDate);


    @Query("""
    SELECT dr
    FROM donation_registration dr
    WHERE dr.donor.userId = :donorId AND dr.status = :status
""")
    List<DonationRegistrationEntity> findUncompletedRegistrations(@Param("donorId") UUID donorId, @Param("status") String status);

    @Query(value =
            "SELECT COALESCE(MONTH(bs.donation_date), MONTH(ebr.registration_date)) AS month, " +
                    "SUM(CASE WHEN dr.status = N'ĐÃ HIẾN' THEN 1 ELSE 0 END) AS successCount, " +
                    "SUM(CASE WHEN dr.status = N'HỦY' THEN 1 ELSE 0 END) AS failedCount " +
                    "FROM donation_registration dr " +
                    "LEFT JOIN blood_donation_schedule bs " +
                    "ON dr.blood_donation_schedule_id = bs.blood_donation_schedule_id " +
                    "LEFT JOIN donation_emergency de " +
                    "ON de.donation_registration_id = dr.donation_registration_id " +
                    "LEFT JOIN emergency_blood_request ebr " +
                    "ON ebr.emergency_blood_request_id = de.emergency_blood_request_id " +
                    "WHERE (YEAR(bs.donation_date) = :year OR YEAR(ebr.registration_date) = :year) " +
                    "GROUP BY COALESCE(MONTH(bs.donation_date), MONTH(ebr.registration_date)) " +
                    "ORDER BY month ASC",
            nativeQuery = true)
    List<Object[]> getMonthlyDonationStats(@Param("year") int year);

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