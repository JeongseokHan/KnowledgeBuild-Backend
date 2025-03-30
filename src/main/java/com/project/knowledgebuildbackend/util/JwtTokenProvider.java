package com.project.knowledgebuildbackend.util;

import com.project.knowledgebuildbackend.exception.CustomException;
import com.project.knowledgebuildbackend.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Long userId) {
        try {
            long now = (new Date()).getTime();

            // Access Token 생성
            Date accessTokenExpiresIn = new Date(now + 1800000); // 86400000는 24시간, 1800000은 30분

            String accessToken = Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            // Refresh Token 생성
            String refreshToken = Jwts.builder()
                    .setExpiration(new Date(now + 604800000)) //  604800000는 일주일
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            return JwtToken.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        catch (JwtException e) {
            log.error("JWT 생성 중 오류 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.ERROR_CREATE_JWT);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 입력값: {}", e.getMessage());
            throw new CustomException(ErrorCode.BAD_REQUEST_USERID);
        }
    }

}
