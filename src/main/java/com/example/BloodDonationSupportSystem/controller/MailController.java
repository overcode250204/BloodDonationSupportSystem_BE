package com.example.BloodDonationSupportSystem.controller;

import com.example.BloodDonationSupportSystem.base.BaseReponse;
import com.example.BloodDonationSupportSystem.dto.mailDTO.ContactRequestDTO;
import com.example.BloodDonationSupportSystem.service.emailservice.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class MailController {

   @Autowired
    private EmailService emailService;



}
