package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.DonorResponse;
import com.example.BloodDonationSupportSystem.entity.OauthAccountEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByOauthAccount(OauthAccountEntity oauthAccountEntity);
    Optional<UserEntity> findByUserId(UUID userId);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = """
    SELECT u.full_name AS fullName,
           u.blood_type AS bloodType,
           MAX(d.date_complete_donation) AS lastDonationDate,
           COALESCE(oa.account, u.phone_number) AS contactInfo
      FROM user_table u
      JOIN donation_registration d ON u.user_id = d.donor_id
      LEFT JOIN oauthaccount oa ON u.user_id = oa.user_id
     WHERE u.status = N'HOẠT ĐỘNG'
       AND u.[role_id] = 2
       AND d.status = N'ĐÃ HIẾN'
       AND d.date_complete_donation <= :threeMonthsAgo
       AND u.blood_type IN (:bloodTypes)
       AND (6371 * acos(
             cos(radians(:fptLat)) *
             cos(radians(CAST(u.latitude AS double precision))) *
             cos(radians(CAST(u.longitude AS double precision)) - radians(:fptLng)) +
             sin(radians(:fptLat)) *
             sin(radians(CAST(u.latitude AS double precision)))
       )) <= :maxDistanceKm
     GROUP BY u.user_id, u.full_name, u.blood_type, u.phone_number, oa.account
""", nativeQuery = true)
    List<Object[]> findEligibleDonors(
            @Param("bloodTypes") List<String> bloodTypes,
            @Param("maxDistanceKm") double maxDistanceKm,
            @Param("fptLat") double fptLat,
            @Param("fptLng") double fptLng,
            @Param("threeMonthsAgo") LocalDate threeMonthsAgo
    );
    @Query(value = """
    SELECT u.*
    FROM user_table u
    JOIN donation_registration d ON d.donor_id = u.user_id
    JOIN donation_process dp ON dp.donation_registration_id = d.donation_registration_id
    WHERE dp.donation_process_id = :processId
    AND d.status = N'ĐÃ HIẾN'
""", nativeQuery = true)
    Optional<UserEntity> findUserByProcessId(@Param("processId") UUID processId);
}
