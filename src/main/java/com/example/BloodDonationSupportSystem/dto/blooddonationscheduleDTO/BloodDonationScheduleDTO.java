package com.example.BloodDonationSupportSystem.dto.blooddonationscheduleDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class BloodDonationScheduleDTO {

    private UUID bloodDonationScheduleId;

    @NotNull(message = "Address hospital can not null")
    private String addressHospital;

    @NotNull(message = "Donation Date can not null")
    private LocalDate donationDate;

    @NotNull(message = "Start Time can not null")
    private Time startTime;

    @NotNull(message = "End Time can not null")
    private Time endTime;

    @NotNull(message = "Amount of Registration can not null")
    private int amountRegistration;

    private int registrationMatching;

    private UUID editedByStaffId;




}
