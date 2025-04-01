package com.project.knowledgebuildbackend.service.user;

import com.project.knowledgebuildbackend.entity.User;
import com.project.knowledgebuildbackend.exception.CustomException;
import com.project.knowledgebuildbackend.exception.ErrorCode;
import com.project.knowledgebuildbackend.repository.UserRepository;
import com.project.knowledgebuildbackend.type.SocialTypeEnum;
import com.project.knowledgebuildbackend.util.CreateUserName;
import com.project.knowledgebuildbackend.util.JWT;
import com.project.knowledgebuildbackend.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final CreateUserName createUserName;
    /**
     * SNS 로그인 성공 후 진입하는 메소드 입니다. snsKind 여부에 따라 처리합니다.
     * @param oAuth2User provider 에게서 받은 유저 정보
     * @param socialType SNS 타입(카카오, 네이버, 구글)
     */
    public JWT userAuth(OAuth2User oAuth2User, String socialType) {
        SocialTypeEnum socialTypeEnum = SocialTypeEnum.valueOf(socialType.toUpperCase());
        String snsKind = socialTypeEnum.getSnsKind(); // 1001 ~ 1003
        return switch (snsKind) {
            case "1001" -> kakaoAuth(oAuth2User, snsKind);
            case "1002" -> naverAuth(oAuth2User, snsKind);
            case "1003" -> googleAuth(oAuth2User, snsKind);
            default -> throw new CustomException(ErrorCode.INVALID_SOCIAL_TYPE);
        };
    }

    public JWT kakaoAuth(OAuth2User oAuth2User, String snsKind) {
        String profilePath = null;
        Long snsId = oAuth2User.getAttribute("id");

        Optional<User> checkUser = userRepository.findBySnsId(snsId.toString());

        if(checkUser.isPresent()) return jwtProvider.generateToken(checkUser.get().getUserId());

        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        if(kakaoAccount == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_SNS_ACCOUNT);
        }

        try {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            profilePath = (String) profile.get("profile_image_url");
        } catch(NullPointerException e) {
            log.info("카카오 프로필 이미지가 존재하지 않습니다.");
        }

        Long userId = createUser(snsId.toString(), snsKind, profilePath);

        return jwtProvider.generateToken(userId);
    }

    public JWT naverAuth(OAuth2User oAuth2User, String snsKind) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");

        if(naverResponse == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_SNS_ACCOUNT);
        }

        String snsId = (String) naverResponse.get("id");

        Optional<User> checkUser = userRepository.findBySnsId(snsId);
        if(checkUser.isPresent()) return jwtProvider.generateToken(checkUser.get().getUserId());

        String profilePath = (String) naverResponse.get("profile_image");

        Long userId = createUser(snsId, snsKind, profilePath);

        return jwtProvider.generateToken(userId);
    }

    public JWT googleAuth(OAuth2User oAuth2User, String snsKind) {
        String snsId = oAuth2User.getAttribute("sub");
        Optional<User> checkUser = userRepository.findBySnsId(snsId);

        if(checkUser.isPresent()) return jwtProvider.generateToken(checkUser.get().getUserId());
        String profilePath = oAuth2User.getAttribute("picture");

        Long userId = createUser(snsId, snsKind, profilePath);
        return jwtProvider.generateToken(userId);
    }

    private Long createUser(String snsId, String snsKind, String profilePath) {
        String nickname;

        do {
            nickname = "User_" + RandomStringUtils.random(5, 48, 122, true, true);
        } while (userRepository.findByNickname(nickname).isPresent());

        User user = new User();
        user.setSnsId(snsId);
        user.setSnsKind(snsKind);
        user.setProfilePath(profilePath);
        user.setNickname(nickname);
        userRepository.save(user);

        return user.getUserId();
    }
}
