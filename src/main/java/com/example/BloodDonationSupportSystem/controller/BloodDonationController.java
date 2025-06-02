package com.example.BloodDonationSupportSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class BloodDonationController {
    @GetMapping
    public String bloodDonation() {
        return "bloodDonation";
    }
}
