package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.service.smsservice.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class SmsController {
    @Autowired
    private SmsService smsService;



}
