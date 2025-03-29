package com.project.knowledgebuildbackend.type;

import com.project.knowledgebuildbackend.exception.CustomException;
import com.project.knowledgebuildbackend.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum SocialTypeEnum {
    KAKAO("1001"),
    NAVER("1002"),
    GOOGLE("1003");

    private final String snsKind;

    SocialTypeEnum(String snsKind) {
        this.snsKind = snsKind;
    }

    public static SocialTypeEnum fromSnsKind(String snsKind) {
        for (SocialTypeEnum type : values()) {
            if (type.getSnsKind().equals(snsKind)) {
                return type;
            }
        }
        throw new CustomException(ErrorCode.INVALID_SOCIAL_TYPE);
    }
}