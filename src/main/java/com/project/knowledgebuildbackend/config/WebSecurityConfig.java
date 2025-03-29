package com.project.knowledgebuildbackend.config;

import com.project.knowledgebuildbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .successHandler(oAuth2LoginSuccessHandler())  // 성공 시 처리
                                .failureHandler(oAuth2LoginFailureHandler())  // 실패 시 처리
                );
        return http.build();
    }

    // OAuth2 로그인 성공 후 처리
    public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String socialType = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
                userService.userAuth(oAuth2User, socialType);
            }
            catch (Exception e) {
                log.info("성공 메소드, 에러 발생 : {}", e.getMessage());
            }
        };
    }

    // OAuth2 로그인 실패 후 처리
    public AuthenticationFailureHandler oAuth2LoginFailureHandler() {
        return (request, response, exception) -> {
            log.info("실패 메소드 : {}", exception.getMessage());
        };
    }
}
