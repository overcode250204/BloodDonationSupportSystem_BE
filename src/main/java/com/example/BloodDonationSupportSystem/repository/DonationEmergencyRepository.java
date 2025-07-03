package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.DonationEmergencyEntity;
import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DonationEmergencyRepository extends JpaRepository<DonationEmergencyEntity, UUID> {
    boolean existsByDonationRegistration_DonationRegistrationId(UUID donationRegistrationId);

    @Query(value = """
    SELECT 
        ebr.*
    FROM emergency_blood_request ebr
    LEFT JOIN donation_emergency de 
        ON ebr.emergency_blood_request_id = de.emergency_blood_request_id
        AND de.donation_registration_id = :donationRegistrationId
    WHERE 
        ebr.blood_type = :donorBloodType
        AND ebr.volume_ml > 0
    ORDER BY 
        ebr.registration_date ASC,
        ebr.level_of_urgency DESC,
        ebr.emergency_blood_request_id ASC
    LIMIT 1
""", nativeQuery = true)
    Optional<EmergencyBloodRequestEntity> findTopEmergencyRequestByDateAndUrgency(
            @Param("donationRegistrationId") UUID donationRegistrationId,
            @Param("donorBloodType") String donorBloodType
    );
}
