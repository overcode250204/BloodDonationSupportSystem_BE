package com.example.BloodDonationSupportSystem.repository.processmanagement;

import com.example.BloodDonationSupportSystem.entity.BloodDonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BloodDonationHistoryRepository extends JpaRepository<BloodDonationHistory, UUID> {
}
