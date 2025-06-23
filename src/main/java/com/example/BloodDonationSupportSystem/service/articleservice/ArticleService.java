package com.example.BloodDonationSupportSystem.service.articleservice;

import com.example.BloodDonationSupportSystem.dto.articleDTO.ArticleDTO;
import com.example.BloodDonationSupportSystem.entity.ArticleEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.ArticleRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${upload.image.path}")
    private String imagePath;

    @Value("${upload.image.base-url}")
    private String baseUrl;



    public ArticleDTO create(ArticleDTO dto) throws IOException {
        ArticleEntity article = new ArticleEntity();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(new Date());
        article.setStatus(dto.getStatus());
        article.setArticleType(dto.getArticleType());

            String base64Image = dto.getImageUrl();
            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            Path uploadDir = Paths.get("images\\uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String filename = UUID.randomUUID() + "_" + dto.getFileName();
            Path filePath = uploadDir.resolve(filename);
            Files.write(filePath, imageBytes);
            article.setImageUrl(filePath.toString());

        article.setCreatedByAdminId(userRepository.findById(dto.getCreatedByAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found")));
        articleRepository.save(article);
        return mapToDTO(article);
    }




    public ArticleDTO getById(UUID id) {
        return articleRepository.findById(id)
                .map(article -> this.mapToDTO(article))
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
    }

    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream().map(article -> this.mapToDTO(article)).toList();
    }


    public ArticleDTO update(UUID id, ArticleDTO dto) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setStatus(dto.getStatus());
        article.setArticleType(dto.getArticleType());
        articleRepository.save(article);
        return mapToDTO(article);
    }

    public String delete(UUID id) throws IOException {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        Path uploadDir = Paths.get(article.getImageUrl());
        Files.deleteIfExists(uploadDir);
        articleRepository.delete(article);
        return "Deleted Article";
    }



    private ArticleDTO mapToDTO(ArticleEntity entity) {
        ArticleDTO response = new ArticleDTO();
        response.setId(entity.getArticleId());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setStatus(entity.getStatus());
        response.setArticleType(entity.getArticleType());
        response.setImageUrl(entity.getImageUrl());
        response.setCreatedByAdminId(entity.getCreatedByAdminId().getUserId());
        return response;
    }



}
