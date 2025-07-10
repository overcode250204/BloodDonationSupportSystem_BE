package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.entity.BloodDonationScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BloodDonationScheduleRepository extends JpaRepository<BloodDonationScheduleEntity, UUID> {
    List<BloodDonationScheduleEntity> findAllByDonationDateBetween(LocalDate startDate, LocalDate endDate);




}
