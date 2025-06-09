package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;

import com.example.BloodDonationSupportSystem.enumentity.BloodTypeEnum;
import com.example.BloodDonationSupportSystem.enumentity.GenderEnum;
import com.example.BloodDonationSupportSystem.enumentity.StatusBloodBagEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BloodBagRequest {

    @NotNull(message = "nhóm máu là bắt buộc")
    private BloodTypeEnum bloodType;

    @NotNull(message = "dung tích túi máu là bắt buộc")
    @Min(value = 250,message = "dung tích túi máu tối thiểu là 250 ml")
    @Max(value= 450,message = "dung tích túi máu tối đa là 450 ml")
    private Integer volume;

    @NotNull(message = "số lượng túi máu là bắt buộc")
    @Min(value = 1,message = "tạo túi máu 1 lần 1 túi thôi nhé")
    private Integer amount_bag;

    @NotNull(message = "ngày tạo là bắt buộc")
    private LocalDate createAt;

    @NotNull(message = "ngày hết hạn là bắt buộc")
    private LocalDate expiredDate;

    @NotNull(message = "trạng thái túi máu là bắt buộc")
    private StatusBloodBagEnum statusBloodBagEnum;

    @NotNull(message = "mã đăng ký hiến máu là bắt buộc")
    private UUID donationId;
}
