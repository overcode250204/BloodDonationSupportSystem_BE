package com.example.BloodDonationSupportSystem.controllers;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.services.userservice.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public BaseReponse<?> profile() {
        UserProfileDTO currentUser = userProfileService.getUserProfile();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get user profile successful", currentUser);
    }

    @PutMapping("/profile")
    public BaseReponse<?> updateProfile(@Valid @RequestBody UserProfileDTO request) {
        UserProfileDTO currentUser = userProfileService.updateUserProfile(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update user profile successful", currentUser);
    }
}
