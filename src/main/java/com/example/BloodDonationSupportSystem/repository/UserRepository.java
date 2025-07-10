package com.example.BloodDonationSupportSystem.repository;

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
    @Query(value = "SELECT COUNT(*) FROM user_table u " +
            "WHERE "+
            " YEAR(u.created_at) = :year " +
            "AND MONTH(u.created_at) = :month",
            nativeQuery = true)
    long countFilter(
            @Param("year") int year,
            @Param("month") int month);

    @Query(value = """
     SELECT u.full_name AS fullName,
               u.blood_type AS bloodType,
               MAX(d.date_complete_donation) AS lastDonationDate,
               u.phone_number AS phoneNumber
        FROM user_table u
        JOIN donation_registration d ON u.user_id = d.donor_id
        WHERE u.status = N'HOẠT ĐỘNG'
          AND u.[role_id]  = 2\s
          AND d.status = N'ĐÃ HIẾN'
          AND d.date_complete_donation <=DATEADD(MONTH, -3, CAST(GETDATE() AS DATE))
          AND u.blood_type IN (:bloodTypes)
          AND (6371 * acos(
                cos(radians(:fptLat)) *
                cos(radians(CAST(u.latitude AS double precision))) *
                cos(radians(CAST(u.longitude AS double precision)) - radians(:fptLng)) +
                sin(radians(:fptLat)) *
                sin(radians(CAST(u.latitude AS double precision)))
          )) <= :maxDistanceKm
        GROUP BY u.user_id, u.full_name, u.blood_type, u.phone_number
    """, nativeQuery = true)
    List<Object[]> findEligibleDonors(
            @Param("bloodTypes") List<String> bloodTypes,
            @Param("maxDistanceKm") double maxDistanceKm,
            @Param("fptLat") double fptLat,
            @Param("fptLng") double fptLng,
            @Param("threeMonthsAgo") LocalDate threeMonthsAgo
    );
}
