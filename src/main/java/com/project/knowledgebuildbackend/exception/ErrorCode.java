package com.project.knowledgebuildbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 SNS 형식입니다.", "B40010");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;
}
