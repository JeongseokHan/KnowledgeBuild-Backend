package com.project.knowledgebuildbackend.util;

import com.project.knowledgebuildbackend.exception.CustomException;
import com.project.knowledgebuildbackend.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JWTProvider {
    private final Key key;

    public JWTProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * UserId를 통해 AccessToken 및 RefreshToken을 생성합니다.
     * @param userId UserId
     * @return JWT 토큰
     */
    public JWT generateToken(Long userId) {
        try {
            String accessToken = Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .setExpiration(new Date(60 * 30 * 1000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setExpiration(new Date(60 * 60 * 24 * 7 * 1000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            return JWT.builder()
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

    /**
     * AccessToken을 통해 UserId를 반환합니다(만료 시간 체크 O)
     * @param accessToken AccessToken
     * @return UserId
     */
    public Long getUserIdByAccessToken(String accessToken) {
        log.info("getUserIdByAccessToken 실행(만료 시간 체크 O) : {}", accessToken);
        Claims claims = parseClaims(accessToken);
        return Long.valueOf(claims.getSubject());
    }

    /**
     * 토큰 만료 여부 확인 후 Claims 또는 Exception 반환
     * @param accessToken AccessToken
     * @return Claims
     */
    private Claims parseClaims(String accessToken) {
        if (accessToken == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        if (accessToken.contains("Bearer")) {
            accessToken = accessToken.split(" ")[1].trim();
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

}
