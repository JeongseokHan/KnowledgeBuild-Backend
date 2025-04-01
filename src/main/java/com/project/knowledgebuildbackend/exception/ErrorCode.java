package com.project.knowledgebuildbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 에러
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 SNS 형식입니다.", "B40000"),
    BAD_REQUEST_USERID(HttpStatus.BAD_REQUEST, "잘못된 유저 ID 입니다.", "B40001"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.", "B40002"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "만료된 엑세스 토큰입니다.", "B40003"),

    // 404 에러
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.", "B40401"),
    NOT_FOUND_SNS_ACCOUNT(HttpStatus.NOT_FOUND, "SNS 사용자의 정보가 존재하지 않습니다.", "B40402"),

    // 500 에러
    ERROR_CREATE_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "JWT 토큰 생성 중 에러가 발생하였습니다.", "I50000");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;
}
