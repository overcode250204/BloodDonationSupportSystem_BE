package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.UpdateUserProfileRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.UserProfileResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.userservice.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('MEMBER')")
@RequiredArgsConstructor
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public BaseReponse<?> profile() {
        UserProfileResponse currentUser = userProfileService.getUserProfile();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get user profile successful", currentUser);
    }

    @PutMapping("/update_profile")
    public BaseReponse<?> updateProfile(@Valid @RequestBody UpdateUserProfileRequest user) {
        UserProfileResponse currentUser = userProfileService.updateUserProfile(user);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update user profile successful", currentUser);
    }
}
