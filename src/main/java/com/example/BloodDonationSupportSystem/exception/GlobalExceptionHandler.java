package com.example.BloodDonationSupportSystem.exception;

import com.example.BloodDonationSupportSystem.dto.common.BaseReponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseReponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Kiểm tra message lỗi để xác định lỗi trùng key khi nó log dưới console nó dựa trên key dulicate nó bắt thấy trả ra resp
        String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        ErrorCode errorCode;
        //trùng key o day
        if (message != null && message.toLowerCase().contains("duplicate")) {
            errorCode = ErrorCode.DUPLICATE_DONATION_REGIS_ID;
        } else {
            // lỗi không xác định
            errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        }

        BaseReponse response = new BaseReponse<>(errorCode.getCode(), errorCode.getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseReponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        // thằng này gom các lỗi từ dto request vô
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        BaseReponse<?> response = new BaseReponse<>(
               HttpStatus.BAD_REQUEST.value(),
                "Xác thực thất bại",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
