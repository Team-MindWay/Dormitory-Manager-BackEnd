package com.example.domaserver.domain.auth.exception;

import com.example.domaserver.global.exception.CustomException;
import com.example.domaserver.global.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
