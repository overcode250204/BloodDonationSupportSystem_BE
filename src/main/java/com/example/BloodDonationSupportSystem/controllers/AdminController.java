package com.example.BloodDonationSupportSystem.controllers;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.services.adminservice.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public BaseReponse<?> getAllUsers() {
        List<UserProfileDTO> users = adminService.getAllUsers();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get all users successful", users);
    }

    @PutMapping("/users/{id}")
    public BaseReponse<?> updateStatusUser(@PathVariable("id") UUID userID, @Valid @RequestBody UserProfileDTO request) {
        UserProfileDTO user = adminService.updateStatusUser(userID, request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update status user successful", user);
    }
}
