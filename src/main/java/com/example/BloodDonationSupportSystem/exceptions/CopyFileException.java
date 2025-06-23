package com.example.BloodDonationSupportSystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopyFileException extends RuntimeException{
    private String message;
}
