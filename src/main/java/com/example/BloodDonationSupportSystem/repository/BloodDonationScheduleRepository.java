package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.BloodDonationScheduleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BloodDonationScheduleRepository extends JpaRepository<BloodDonationScheduleEntity, UUID> {
//    @Query(value = "SELECT bs.donation_date AS date, " +
//            "bs.address_hospital, " +
//            "COUNT(dr.donation_registration_id) AS total_registration, " +
//            "SUM(CASE WHEN dr.status = N'ĐÃ HIẾN' THEN 1 ELSE 0 END) AS total_success, " +
//            "SUM(CASE WHEN dr.status = N'HỦY' THEN 1 ELSE 0 END) AS total_failed, " +
//            "SUM(CASE WHEN dp.volume_ml IS NOT NULL THEN dp.volume_ml ELSE 0 END) AS total_volume " +
//            "FROM blood_donation_schedule bs " +
//            "LEFT JOIN donation_registration dr ON dr.blood_donation_schedule_id = bs.blood_donation_schedule_id " +
//            "LEFT JOIN donation_process dp ON dp.donation_registration_id = dr.donation_registration_id " +
//            "WHERE bs.donation_date BETWEEN :startDate AND :endDate " +
//            "GROUP BY bs.donation_date, bs.address_hospital " +
//            "ORDER BY bs.donation_date ASC, bs.address_hospital ASC",
//            nativeQuery = true)
@Query(value =
        "SELECT " +
                "u.full_name AS donor_name, " +
                "u.phone_number AS donor_phone, " +
                "o.account AS donor_account, " +
                "u.address AS donor_address, " +
                "u.blood_type AS donor_blood_type, " +
                "dp.volume_ml AS donated_volume, " +
                "bs.donation_date, " +
                "bs.address_hospital, " +
                "dr.status " +
                "FROM donation_registration dr " +
                "JOIN user_table u ON u.user_id = dr.donor_id " +
                "LEFT JOIN oauthaccount o ON o.user_id = u.user_id " +
                "LEFT JOIN blood_donation_schedule bs ON bs.blood_donation_schedule_id = dr.blood_donation_schedule_id " +
                "LEFT JOIN donation_process dp ON dp.donation_registration_id = dr.donation_registration_id " +
                "WHERE bs.donation_date BETWEEN :startDate AND :endDate " +
                "ORDER BY bs.donation_date ASC",
        nativeQuery = true)
    List<Object[]> getDonationReport(@Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate);



    List<BloodDonationScheduleEntity> findAllByDonationDateBetween(LocalDate startDate, LocalDate endDate);



}
