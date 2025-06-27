package com.example.BloodDonationSupportSystem.repository;

import com.example.BloodDonationSupportSystem.dto.reportDTO.DonationRegistrationReportDTO;
import com.example.BloodDonationSupportSystem.entity.DonationRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface DonationRegistrationRepository extends JpaRepository<DonationRegistrationEntity, UUID> {
    List<DonationRegistrationReportDTO> getDonationReport(@Param("from") Date from, @Param("to") Date to);
}
