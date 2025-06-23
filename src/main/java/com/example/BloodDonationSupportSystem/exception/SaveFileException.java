package com.example.BloodDonationSupportSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveFileException extends RuntimeException{
    private String message;
}
