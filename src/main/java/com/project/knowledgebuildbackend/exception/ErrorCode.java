package com.project.knowledgebuildbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 SNS 형식입니다.", "B40000"),
    BAD_REQUEST_USERID(HttpStatus.BAD_REQUEST, "잘못된 유저 ID 입니다.", "B40001"),

    ERROR_CREATE_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "JWT 토큰 생성 중 에러가 발생하였습니다.", "I50000");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;
}
