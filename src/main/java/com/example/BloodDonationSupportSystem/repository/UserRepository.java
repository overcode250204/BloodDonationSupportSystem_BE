package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.OauthAccountEntity;
import com.example.BloodDonationSupportSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByOauthAccount(OauthAccountEntity oauthAccountEntity);
    Optional<UserEntity> findByUserId(UUID userId);
    boolean existsByPhoneNumber(String phoneNumber);
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
