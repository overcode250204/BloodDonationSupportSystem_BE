package com.example.BloodDonationSupportSystem.dto.authenaccountDTO.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SearchDistanceRequest {

    @Min(value = 1, message = "Distance must be at least 1")
    @Max(value = 50, message = "Distance must be less than or equal to 50")
    private double distance;

   @Size(min =1,max =8,message = "Blood type list must have at least 1 and at most 8 blood types")
   @NotEmpty(message = "Need at least 1 blood type!")
   private List<String> bloodTypes;
}
