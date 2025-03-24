package com.example.domaserver.domain.user.exception;

public class InvalidUserExeption extends RuntimeException {
    public InvalidUserExeption(String message) {
        super(message);
    }
}
