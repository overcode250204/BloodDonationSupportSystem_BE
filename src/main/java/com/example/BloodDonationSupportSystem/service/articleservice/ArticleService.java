package com.example.BloodDonationSupportSystem.service.articleservice;

import com.example.BloodDonationSupportSystem.dto.articleDTO.ArticleDTO;
import com.example.BloodDonationSupportSystem.entity.ArticleEntity;
import com.example.BloodDonationSupportSystem.exception.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repository.ArticleRepository;
import com.example.BloodDonationSupportSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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



    public ArticleDTO create(ArticleDTO dto, MultipartFile image) {
        ArticleEntity article = new ArticleEntity();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(new Date());
        article.setStatus(dto.getStatus());
        article.setArticleType(dto.getArticleType());
        if (image != null && !image.isEmpty()) {
            String imageUrl =storeImage(image);
            article.setImageUrl(imageUrl);
        }

        article.setCreatedByAdminId(userRepository.findById(dto.getCreatedByAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found")));
        articleRepository.save(article);
        return mapToDTO(article);
    }

    private String storeImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        try {
            Path path = Paths.get(imagePath).resolve(fileName);
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return baseUrl + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image " + image.getOriginalFilename());
        }

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
        articleRepository.save(article);
        return mapToDTO(article);
    }

    public String delete(UUID id) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        articleRepository.delete(article);
        return "Deleted Article";
    }

    private ArticleDTO mapToDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus());
        dto.setArticleType(entity.getArticleType());
        dto.setCreatedByAdminId(entity.getCreatedByAdminId().getUserId());
        return dto;
    }



}
