package com.example.BloodDonationSupportSystem.repository.processmanagement;

import com.example.BloodDonationSupportSystem.entity.MemberScreening;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.MemberScreeningStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberScreeningRepository extends JpaRepository<MemberScreening, UUID> {
    List<MemberScreening> findByStatus(MemberScreeningStatus status);

}
