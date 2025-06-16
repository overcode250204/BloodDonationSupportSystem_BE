package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.UpdateUserProfileRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.UserProfileResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.userservice.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('MEMBER')")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public BaseReponse<?> profile() {
        UserProfileResponse currentUser = userProfileService.getUserProfile();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get user profile successful", currentUser);
    }

    @PutMapping("/profile")
    public BaseReponse<?> updateProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
        UserProfileResponse currentUser = userProfileService.updateUserProfile(request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update user profile successful", currentUser);
    }
}
