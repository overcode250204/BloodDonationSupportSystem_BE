package com.example.BloodDonationSupportSystem.repository;


import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
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

@Query(value = "SELECT MONTH(bs.donation_date) AS month, " +
        "SUM(CASE WHEN dr.status = N'ĐÃ HIẾN' THEN 1 ELSE 0 END) AS successCount, " +
        "SUM(CASE WHEN dr.status = N'HỦY' THEN 1 ELSE 0 END) AS failedCount " +
        "FROM donation_registration dr " +
        "JOIN blood_donation_schedule bs on dr.blood_donation_schedule_id = bs.blood_donation_schedule_id " +
        "WHERE YEAR(bs.donation_date) = :year " +
        "GROUP BY MONTH(bs.donation_date) " +
        "ORDER BY month ASC",
        nativeQuery = true)
List<Object[]> getMonthlyDonationStats(@Param("year") int year);

}
