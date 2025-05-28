package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.RoleEntity;
import com.example.BloodDonationSupportSystem.enumentity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(RoleEnum roleEnum);
}
