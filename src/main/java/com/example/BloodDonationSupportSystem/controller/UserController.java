package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.service.userservice.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@Tag(name = "UserController")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public BaseReponse<?> profile() {
        UserProfileDTO currentUser = userService.getCurrentUserProfile();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get current user successful", currentUser);
    }
    @PutMapping("/profile")
    public BaseReponse<?> updateProfile(@RequestBody UserProfileDTO request) {
        UserProfileDTO currentUser = userService.updateUserProfile(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update user profile successful", currentUser);
    }
}
