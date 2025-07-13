package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {
//    @Query(value =
//            "SELECT bi.blood_type_id, " +
//                    "COALESCE(SUM(CASE " +
//                    "    WHEN dp.blood_test = N'ĐÃ ĐẠT' AND " +
//                    "        ((bs.donation_date <= :endDate AND bs.donation_date IS NOT NULL) " +
//                    "         OR (ebr.registration_date <= :endDate AND ebr.registration_date IS NOT NULL)) " +
//                    "    THEN dp.volume_ml ELSE 0 " +
//                    "END), 0) AS total_volume " +
//                    "FROM blood_inventory bi " +
//                    "LEFT JOIN donation_process dp ON dp.blood_type_id = bi.blood_type_id " +
//                    "LEFT JOIN donation_registration dr ON dp.donation_registration_id = dr.donation_registration_id " +
//                    "LEFT JOIN blood_donation_schedule bs ON dr.blood_donation_schedule_id = bs.blood_donation_schedule_id " +
//                    "LEFT JOIN donation_emergency de ON de.donation_registration_id = dr.donation_registration_id " +
//                    "LEFT JOIN emergency_blood_request ebr ON ebr.emergency_blood_request_id = de.emergency_blood_request_id " +
//                    "GROUP BY bi.blood_type_id " +
//                    "ORDER BY bi.blood_type_id ASC",
//            nativeQuery = true)
    @Query(value = "SELECT bi.blood_type_id, " +
            "COALESCE(SUM(CASE " +
            "WHEN bs.donation_date <= :endDate AND dp.blood_test = N'ĐÃ ĐẠT' THEN dp.volume_ml " +
            "ELSE 0 END), 0) AS total_volume " +
            "FROM blood_inventory bi " +
            "LEFT JOIN donation_process dp ON dp.blood_type_id = bi.blood_type_id " +
            "LEFT JOIN donation_registration dr ON dp.donation_registration_id = dr.donation_registration_id " +
            "LEFT JOIN blood_donation_schedule bs ON bs.blood_donation_schedule_id = dr.blood_donation_schedule_id " +
            "GROUP BY bi.blood_type_id " +
            "ORDER BY bi.blood_type_id ASC",
            nativeQuery = true)
    List<Object[]> getBloodVolume(@Param("endDate") Date endDate);
}
