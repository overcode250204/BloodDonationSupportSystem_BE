package com.example.BloodDonationSupportSystem.dto.donationregistrationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DonationRegistrationDTO {

    private LocalDate registrationDate;

    private LocalDate completeDonationDate;

    @NotNull(message = "Status can not null!")
    private String status;
    @NotNull(message = "Start date can not null!")
    private LocalDate startDate;

    @NotNull(message = "End date can not null!")
    private LocalDate endDate;

    private UUID screenedByStaffId;

    @NotNull(message = "This registration form must to assigned with specific member!!!")
    private UUID donorId;

    private UUID bloodDonationScheduleId;
}
