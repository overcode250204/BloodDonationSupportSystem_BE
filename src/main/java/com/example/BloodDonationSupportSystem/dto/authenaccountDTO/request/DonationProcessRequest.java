package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;


import java.util.UUID;

public class DonationProcessRequest {
    private String isPassed;
    private int volumeMl;
    private String status;
    private String typeDonation;
    private UUID donationRegistrationProcessId;
    private String bloodTypeId;
}
