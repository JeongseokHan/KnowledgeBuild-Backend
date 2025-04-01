package com.project.knowledgebuildbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.knowledgebuildbackend.filter.AuthenticationFilter;
import com.project.knowledgebuildbackend.service.user.UserAuthService;
import com.project.knowledgebuildbackend.util.JWT;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
    private final AuthenticationFilter authenticationFilter;
    private final UserAuthService userAuthService;

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
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // OAuth2 로그인 성공 후 처리
    public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String socialType = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
                JWT token = userAuthService.userAuth(oAuth2User, socialType);

                Cookie refreshTokenCookie = new Cookie("refreshToken", token.getRefreshToken());
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setMaxAge(604800);
                response.addCookie(refreshTokenCookie);

                response.setStatus(HttpStatus.OK.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(new ObjectMapper().writeValueAsString(token.getAccessToken()));
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
