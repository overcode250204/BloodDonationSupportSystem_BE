package com.example.BloodDonationSupportSystem.services.articleservice;

import com.example.BloodDonationSupportSystem.dto.articleDTO.ArticleDTO;
import com.example.BloodDonationSupportSystem.entities.ArticleEntity;
import com.example.BloodDonationSupportSystem.exceptions.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.repositories.ArticleRepository;
import com.example.BloodDonationSupportSystem.repositories.UserRepository;
import com.example.BloodDonationSupportSystem.services.filestorageservice.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;


    public ArticleDTO create(ArticleDTO dto) {
        ArticleEntity article = new ArticleEntity();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(new Date());
        article.setStatus(dto.getStatus());
        article.setImage(dto.getImage().getOriginalFilename());
        article.setArticleType(dto.getArticleType());
        article.setCreatedByAdminId(userRepository.findById(dto.getCreatedByAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found")));
        fileStorageService.saveFile(dto.getImage());
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
