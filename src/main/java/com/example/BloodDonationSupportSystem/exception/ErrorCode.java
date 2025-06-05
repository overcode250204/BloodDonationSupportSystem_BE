package com.example.BloodDonationSupportSystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error.", HttpStatus.INTERNAL_SERVER_ERROR),
   DUPLICATE_DONATION_REGIS_ID(1010, "Trùng mã đăng kí hiến máu !", HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus statusCode;
    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
