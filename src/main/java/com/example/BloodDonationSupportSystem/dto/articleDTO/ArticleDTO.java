package com.example.BloodDonationSupportSystem.dto.articleDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ArticleDTO {

    @NotBlank(message = "Title cannot blank")
    private String title;

    private String content;

    @NotNull(message = "Status cannot null")
    private String status;


    private MultipartFile image;

    @NotNull(message = "Must be have article type")
    private String articleType;

    @NotNull(message = "Must be have created by admin id")
    private UUID createdByAdminId;

}
