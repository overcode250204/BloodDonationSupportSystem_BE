package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;


import com.example.BloodDonationSupportSystem.enumentity.StatusBloodBagEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BloodBagRequest {
    @NotBlank(message = "nhóm máu là bắt buộc")
    @Pattern(regexp = "^(A\\+|A\\-|B\\+|B\\-|AB\\+|AB\\-|O\\+|O\\-)$",
            message = "nhóm máu không hợp lệ, phải là : A+, A-, B+, B-, AB+, AB-, O+, O-")
    private String bloodType;

    @NotNull(message = "dung tích túi máu là bắt buộc")
    @Min(value = 250,message = "dung tích túi máu tối thiểu là 250 ml")
    @Max(value= 500,message = "dung tích túi máu tối đa là 500 ml")
    private Integer volume;

    @NotNull(message = "số lượng túi máu là bắt buộc")
    @Min(value = 1,message = "tạo túi máu 1 lần 1 túi thôi nhé")
    @Max(value = 1, message = "tạo túi máu 1 lần 1 túi thôi nhé")
    private Integer amount_bag;

    @NotNull(message = "ngày tạo là bắt buộc")
    private LocalDate createAt;

    @NotNull(message = "ngày hết hạn là bắt buộc")
    private LocalDate expiredDate;

    @NotBlank(message = "trạng thái túi máu là bắt buộc")
    @Pattern(regexp = "^(Còn hạn|Hết hạn|Đã sử dụng)$",
            message = "Trạng thái phải là Còn hạn,Hết hạn,Đã sử dụng")
    private String status;

    @NotNull(message = "mã đăng ký hiến máu là bắt buộc")
    private UUID donationId;
}
