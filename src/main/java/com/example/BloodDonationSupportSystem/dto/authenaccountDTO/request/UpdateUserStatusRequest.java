package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserStatusRequest {
    @NotNull(message = "User ID is null")
    private UUID userId;

    @NotNull(message = "Status is null")
    @Pattern(
            regexp = "HOẠT ĐỘNG|BỊ CẤM|VÔ HIỆU HÓA",
            message = "Trạng thái phải là: HOẠT ĐỘNG, BỊ CẤM hoặc VÔ HIỆU HÓA"
    )
    private String status;
}
