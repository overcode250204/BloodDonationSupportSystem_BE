package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;

import com.example.BloodDonationSupportSystem.enumentity.BloodTypeEnum;
import com.example.BloodDonationSupportSystem.enumentity.StatusBloodBagEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BloodBagResponse {
    private UUID bloodBagId;
    private BloodTypeEnum bloodType;
    private int volume;
    private int amountBag;
    private LocalDate createdAt;
    private LocalDate expiredDate;
    private StatusBloodBagEnum status;
//    private UUID donationRegistrationId;
}
