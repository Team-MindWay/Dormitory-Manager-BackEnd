package com.example.domaserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    MEMBER_NOT_FOUND(404, "해당 ID의 유저를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND_BY_USERNAME(404, "해당 이름의 유저를 찾을 수 없습니다."),

    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN_TYPE(401, "유효하지 않은 토큰 타입입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(401, "만료된 리프레쉬 토큰입니다."),

    EMAIL_ALREADY_EXISTS(409, "이미 존재하는 이메일입니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),

    INTERNAL_SERVER_ERROR(500, "예기치 못한 서버 에러");

    private final int httpStatus;
    private final String message;
}
