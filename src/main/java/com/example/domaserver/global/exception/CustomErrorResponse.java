package com.example.domaserver.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class CustomErrorResponse {
    private int status;
    private String message;
    private String code;

    public static ResponseEntity<CustomErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(CustomErrorResponse.builder()
                    .status(e.getHttpStatus())
                    .code(e.name())
                    .message(e.getMessage())
                    .build()
            );
    }
}

