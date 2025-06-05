package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import com.example.BloodDonationSupportSystem.enumentity.BloodTypeEnum;
import com.example.BloodDonationSupportSystem.enumentity.GenderEnum;
import com.example.BloodDonationSupportSystem.enumentity.StatusBloodBagEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BloodBagRequest {

    @NotNull(message = "nhóm máu là bắt buộc")
    private BloodTypeEnum bloodType;

    @NotNull(message = "số lượng túi máu là bắt buộc")
    private int amount_bag;

    @NotNull(message = "dung tích túi máu là bắt buộc")
    private int volume;

    @NotNull(message = "ngày tạo là bắt buộc")
    private LocalDate createAt;

    @NotNull(message = "ngày hết hạn là bắt buộc")
    private LocalDate expiredDate;

    @NotNull(message = "trạng thái túi máu là bắt buộc")
    private StatusBloodBagEnum statusBloodBagEnum;

    @NotNull(message = "mã đăng ký hiến máu là bắt buộc")
    private UUID donationId;
}
