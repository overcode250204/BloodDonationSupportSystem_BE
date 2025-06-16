package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.Utils.AuthUtils;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.UserProfileDTO;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public BaseReponse<?> profile() {
        UserProfileDTO currentUser = userService.getCurrentUserProfile();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get current user successful", currentUser);
    }

}
