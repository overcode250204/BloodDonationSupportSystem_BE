package com.example.BloodDonationSupportSystem.repository.processmanagement;

import com.example.BloodDonationSupportSystem.entity.BloodDonationProcess;
import com.example.BloodDonationSupportSystem.enumentity.processmanagement.BloodDonationProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BloodDonationProcessRepository extends JpaRepository<BloodDonationProcess, UUID> {
     List<BloodDonationProcess> findByStatus(BloodDonationProcessStatus status);
}
