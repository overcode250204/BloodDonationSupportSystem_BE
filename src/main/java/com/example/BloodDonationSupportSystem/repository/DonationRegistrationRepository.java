package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.reportDTO.DonationRegistrationReportDTO;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
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
//    List<DonationRegistrationReportDTO> getDonationReport(@Param("from") Date from, @Param("to") Date to);
    
}
