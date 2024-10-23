package com.example.domaserver.domain.auth.exception;

import com.example.domaserver.global.exception.CustomException;
import com.example.domaserver.global.exception.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
