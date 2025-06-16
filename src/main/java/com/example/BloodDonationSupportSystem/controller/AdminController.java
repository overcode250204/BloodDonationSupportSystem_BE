package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request.UpdateUserStatusRequest;
import com.example.BloodDonationSupportSystem.dto.authenaccountDTO.response.UserAccountResponse;
import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import com.example.BloodDonationSupportSystem.service.adminservice.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/user")
    public BaseReponse<?> getAllUser() {
        List<UserAccountResponse> users = adminService.getAllUser();
        return new BaseReponse<>(HttpStatus.OK.value(), "Get all users successful", users);
    }

    @PutMapping("/users/{id}")
    public BaseReponse<?> updateStatusUser(@PathVariable("id") UUID userID, @Valid @RequestBody UpdateUserStatusRequest request) {
        UserAccountResponse user = adminService.updateStatusUser(userID, request);
        return new BaseReponse<>(HttpStatus.OK.value(), "Update status user successful", user);
    }
}
