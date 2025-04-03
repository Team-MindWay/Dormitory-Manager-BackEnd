package com.example.domaserver.domain.user.exception;

public class AlreadExistsException extends RuntimeException {
    public AlreadExistsException(String message) {
        super(message);
    }
}
