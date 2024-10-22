package com.example.domaserver.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e) {
        return CustomErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
