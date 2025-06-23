package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.GoogleTokenRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.LoginRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.RegisterRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.LoginAccountResponse;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.RegisterAccountReponse;
import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.service.authaccountservice.AuthAccountService;
import com.example.BloodDonationSupportSystem.service.authaccountservice.GoogleOAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthAccountController {

    @Autowired
    private AuthAccountService authAccountService;
    @Autowired
    private GoogleOAuthService googleOAuthService;

    @PostMapping("/register")
    public BaseReponse<?> register(@Valid @RequestBody RegisterRequest authAccountRequest) {
        RegisterAccountReponse data = authAccountService.register(authAccountRequest);
        return new BaseReponse<>(HttpStatus.OK.value(), "Registration Successful", data);
    }


    @PostMapping("/login")
    public BaseReponse<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginAccountResponse data = authAccountService.authAccount(loginRequest);
        return new BaseReponse<>(HttpStatus.OK.value(), "Login success", data);
    }


    @PostMapping("/google/callback")
    public BaseReponse<?> handleGoogleCallback(@RequestBody GoogleTokenRequest request) {
        return googleOAuthService.handleGoogleCallback(request.getCredential());
    }




}
