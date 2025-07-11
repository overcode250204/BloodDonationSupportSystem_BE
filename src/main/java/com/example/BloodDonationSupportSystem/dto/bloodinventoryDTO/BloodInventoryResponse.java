package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BloodInventoryResponse {
    private String bloodBagId;
    private int totalVolmeMl;
}
