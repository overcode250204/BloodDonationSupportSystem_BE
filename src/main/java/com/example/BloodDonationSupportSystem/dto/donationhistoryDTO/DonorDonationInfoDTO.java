package com.example.BloodDonationSupportSystem.dto.donationhistoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Data
public class DonorDonationInfoDTO {
    private UUID donationRegistrationId;
    private String status;
    private String addressHospital;
    private Time startTime;
    private Time endTime;
    private int volumeMl;
    private Date donationDate;
    private Date registrationDate;

    public DonorDonationInfoDTO(UUID donationRegistrationId,
                                String status,
                                String addressHospital,
                                Time startTime,
                                Time endTime,
                                int volumeMl,
                                Date donationDate,
                                Date registrationDate) {
        this.donationRegistrationId = donationRegistrationId;
        this.status = status;
        this.addressHospital = addressHospital;
        this.startTime = startTime;
        this.endTime = endTime;
        this.volumeMl = volumeMl;
        this.donationDate = donationDate;
        this.registrationDate = registrationDate;
    }
}
