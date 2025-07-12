package com.example.BloodDonationSupportSystem.scheduler;

import com.example.BloodDonationSupportSystem.repository.DonationRegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DonationRegistrationCleanupScheduler {
    @Autowired
    private DonationRegistrationRepository donationRegistrationRepository;

    @Scheduled(cron = "0 0 2 * * *")//Run at 2AM everyday
    @Transactional
    public void markExpiredRegistrationsAsCancelled() {
        LocalDate cutoffDate = LocalDate.now().minusDays(15);
        donationRegistrationRepository.updateStatusToCancelledIfExpired(cutoffDate, "HỦY", "CHƯA HIẾN");
    }

}
