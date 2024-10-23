package com.example.domaserver.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(int status, String message) {
}
