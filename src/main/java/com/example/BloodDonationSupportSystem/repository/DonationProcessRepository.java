package com.example.BloodDonationSupportSystem.repository;


import com.example.BloodDonationSupportSystem.entity.DonationProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface DonationProcessRepository extends JpaRepository<DonationProcessEntity, UUID> {

}
