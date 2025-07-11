package com.example.BloodDonationSupportSystem.dto.mailDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class ContactRequestDTO {
    @NotBlank(message = "Donor Name cannot be blank")
    private String donorName;
    @NotBlank
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Loai mau khong hop le")
    private String bloodType;
    @NotBlank(message = "Contact cannot be blank")
    @Pattern(
            regexp = "^.+@.+\\..+$",
            message = "Email không hợp lệ"
    )
    private String contact;
}
