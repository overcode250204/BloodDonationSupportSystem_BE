package com.example.BloodDonationSupportSystem.dto.articleDTO.response;

import lombok.Data;

import java.util.UUID;
@Data
public class ArticleResponse {
    private UUID id;

    private String title;

    private String content;

    private String status;

    private String imageUrl;

    private String articleType;
}
