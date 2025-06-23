package com.example.BloodDonationSupportSystem.services.filestorageservice;

import com.example.BloodDonationSupportSystem.exceptions.ResourceNotFoundException;
import com.example.BloodDonationSupportSystem.exceptions.SaveFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    @Value("${upload.path}")
    private String rootPath;

    private Path root;

    public void init() {
        try {
            root = Paths.get(rootPath);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }


        } catch (Exception e) {
            throw new ResourceNotFoundException("Could not initialize the file storage");
        }
    }

    public String saveFile(MultipartFile file) {
        init();
            try {
                Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SaveFileException(e.getMessage());
            }
        return file.getOriginalFilename();





    }


    public Resource loadFile(String filename) {
        init();
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found: " + filename);
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("File not found: " + filename);
        }


    }



}
