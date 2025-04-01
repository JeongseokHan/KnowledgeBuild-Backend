package com.project.knowledgebuildbackend.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class JWT {
    private String grantType; // 인증 방식(Bearer 사용)
    private String accessToken; // 인증이 성공한 사용자에게 발급되는 토큰
    private String refreshToken; // AccessToken 갱신을 위해 사용하는 토큰
}
