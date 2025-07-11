package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {
    @Query(value = "SELECT bi.blood_type_id, COALESCE(SUM(dp.volume_ml), 0) AS total_volume " +
            "FROM blood_inventory bi " +
            "LEFT JOIN donation_process dp ON dp.blood_type_id = bi.blood_type_id AND dp.blood_test = N'ĐÃ ĐẠT' " +
            "LEFT JOIN donation_registration dr ON dp.donation_registration_id = dr.donation_registration_id " +
            "LEFT JOIN blood_donation_schedule bs ON bs.blood_donation_schedule_id = dr.blood_donation_schedule_id AND bs.donation_date <= :endDate " +
            "GROUP BY bi.blood_type_id " +
            "ORDER BY bi.blood_type_id ASC",
            nativeQuery = true)
    List<Object[]> getCumulativeVolume(@Param("endDate") Date endDate);
}
