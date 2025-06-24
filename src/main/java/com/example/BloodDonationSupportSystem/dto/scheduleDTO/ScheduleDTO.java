package com.example.BloodDonationSupportSystem.dto.scheduleDTO;

import com.example.BloodDonationSupportSystem.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class ScheduleDTO {
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

    @NotNull(message = "Edited By StaffId can not null")
    private UserEntity editedByStaffId;




}
