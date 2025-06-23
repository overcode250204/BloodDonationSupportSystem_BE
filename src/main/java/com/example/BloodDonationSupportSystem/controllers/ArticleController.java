package com.example.BloodDonationSupportSystem.controllers;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.articleDTO.ArticleDTO;
import com.example.BloodDonationSupportSystem.services.articleservice.ArticleService;

import com.example.BloodDonationSupportSystem.services.filestorageservice.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "ArticleController")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/images")
    public BaseReponse<?> addImage(MultipartFile file) {
        String name = fileStorageService.saveFile(file);
        return new BaseReponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), name);
    }

    @PostMapping( "/article")
    public BaseReponse<?> create(ArticleDTO dto) {
        ArticleDTO response = articleService.create(dto);
        return new BaseReponse<>(HttpStatus.OK.value(), "Create article successfully", response);
    }


    @PutMapping("/article/{id}")
    public BaseReponse<?> update(@RequestBody @Valid ArticleDTO dto, @PathVariable UUID id) {
        ArticleDTO response = articleService.update(id, dto);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update article successfully", response);
    }

    @DeleteMapping("/article/{id}")
    public BaseReponse<?> delete(@PathVariable UUID id) {
        String response = articleService.delete(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "Delete article successfully", response);
    }

    @GetMapping("/article/{id}")
    public BaseReponse<?> get(@PathVariable UUID id) {
        ArticleDTO response = articleService.getById(id);
        return new BaseReponse<>(HttpStatus.OK.value(), "Get article successfully", response);
    }

    @GetMapping("/articles")
    public BaseReponse<?> getAllArticles() {
        List<ArticleDTO> response = articleService.getAll();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get all articles successfully", response);
    }


}
