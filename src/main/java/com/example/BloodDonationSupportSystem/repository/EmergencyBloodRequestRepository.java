package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.EmergencyBloodRequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface EmergencyBloodRequestRepository extends JpaRepository<EmergencyBloodRequestEntity, UUID> {

    @Query("""
    SELECT ebr
    FROM emergency_blood_request ebr
    WHERE ebr.isFulfill = false
""")
    List<EmergencyBloodRequestEntity> getAllIsFulfillEmergencyBloodRequests();

    @Modifying
    @Query("""
    UPDATE emergency_blood_request
    SET isFulfill = true
    WHERE emergencyBloodRequestId IN (
        SELECT ebr.emergencyBloodRequestId
        FROM emergency_blood_request ebr
        JOIN donation_emergency de ON ebr.emergencyBloodRequestId = de.emergencyBloodRequest.emergencyBloodRequestId
        JOIN donation_registration dr ON dr.donationRegistrationId = de.donationRegistration.donationRegistrationId
        JOIN donation_process dp ON dp.donationRegistrationProcess.donationRegistrationId = dr.donationRegistrationId
        WHERE dr.status = :donationRegistrationStatus AND dp.status = :donationProcessStatus AND dp.bloodTest = :bloodTest
        GROUP BY ebr.emergencyBloodRequestId, ebr.volumeMl
        HAVING SUM(dp.volumeMl) >= ebr.volumeMl
    )

""")
    void markFulfilledRequests(@Param("donationRegistrationStatus") String donationRegistrationStatus, @Param("donationProcessStatus") String donationProcessStatus, @Param("bloodTest") String bloodTest);
    @Query("""
    SELECT ebr
    FROM emergency_blood_request ebr
    WHERE ebr.emergencyBloodRequestId IN (
        SELECT ebr.emergencyBloodRequestId
        FROM emergency_blood_request ebr
        JOIN donation_emergency de ON ebr.emergencyBloodRequestId = de.emergencyBloodRequest.emergencyBloodRequestId
        JOIN donation_registration dr ON dr.donationRegistrationId = de.donationRegistration.donationRegistrationId
        JOIN donation_process dp ON dp.donationRegistrationProcess.donationRegistrationId = dr.donationRegistrationId
        WHERE dr.status = :donationRegistrationStatus
          AND dp.status = :donationProcessStatus 
          AND dp.bloodTest = :bloodTest
        GROUP BY ebr.emergencyBloodRequestId, ebr.volumeMl
        HAVING SUM(dp.volumeMl) >= ebr.volumeMl
    )
""")
    List<EmergencyBloodRequestEntity> getFulfilledRequestsToUpdate(
            @Param("donationRegistrationStatus") String donationRegistrationStatus,
            @Param("donationProcessStatus") String donationProcessStatus,
            @Param("bloodTest") String bloodTest
    );

}
