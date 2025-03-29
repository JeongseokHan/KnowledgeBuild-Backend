package com.project.knowledgebuildbackend.service;

import com.project.knowledgebuildbackend.type.SocialTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    /**
     * SNS 로그인 성공 후 진입하는 메소드 입니다. snsKind 여부에 따라 처리합니다.
     * @param oAuth2User provider 에게서 받은 유저 정보
     * @param socialType SNS 타입(카카오, 네이버, 구글)
     */
    public void userAuth(OAuth2User oAuth2User, String socialType) {
        SocialTypeEnum socialTypeEnum = SocialTypeEnum.valueOf(socialType.toUpperCase());
        String snsKind = socialTypeEnum.getSnsKind(); // 1001 ~ 1003
        log.info(snsKind);
    }
}
