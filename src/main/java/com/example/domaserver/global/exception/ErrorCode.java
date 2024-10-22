package com.example.domaserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {;

    private final int httpStatus;
    private final String message;
}
