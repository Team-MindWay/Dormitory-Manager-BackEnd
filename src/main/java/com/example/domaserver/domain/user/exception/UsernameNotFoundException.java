package com.example.domaserver.domain.user.exception;

import com.example.domaserver.global.exception.CustomException;
import com.example.domaserver.global.exception.ErrorCode;

public class UsernameNotFoundException extends CustomException {
    public UsernameNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND_BY_USERNAME);
    }
} 