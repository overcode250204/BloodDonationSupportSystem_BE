package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.articleDTO.ArticleDTO;
import com.example.BloodDonationSupportSystem.exception.BadRequestException;
import com.example.BloodDonationSupportSystem.service.articleservice.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "ArticleController")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PutMapping("/article/{id}")
    public BaseReponse<?> update(@RequestBody @Valid ArticleDTO dto, @PathVariable UUID id) {
        ArticleDTO response = articleService.update(id, dto);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update article successfully", response);
    }

    @DeleteMapping("/article/{id}")
    public BaseReponse<?> delete(@PathVariable UUID id) throws IOException {
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

    @PostMapping("/article/create")
    public BaseReponse<ArticleDTO> create(@RequestBody @Valid ArticleDTO req) throws IOException {
        ArticleDTO response = articleService.create(req);
        return new BaseReponse<>(HttpStatus.OK.value(), "Create article successfully", response);
    }


}
