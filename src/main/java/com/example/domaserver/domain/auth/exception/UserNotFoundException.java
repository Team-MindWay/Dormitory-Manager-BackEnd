package com.example.domaserver.domain.auth.exception;

import com.example.domaserver.global.exception.CustomException;
import com.example.domaserver.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
