package com.project.knowledgebuildbackend.filter;

import com.project.knowledgebuildbackend.dto.CustomUserDetails;
import com.project.knowledgebuildbackend.entity.User;
import com.project.knowledgebuildbackend.exception.CustomException;
import com.project.knowledgebuildbackend.exception.ErrorCode;
import com.project.knowledgebuildbackend.repository.UserRepository;
import com.project.knowledgebuildbackend.util.JWTProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return (method.equals("GET"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(
                    ErrorCode.INVALID_TOKEN.getMessage()
            );
            response.getWriter().flush();
            return;
        }

        try {
            Authentication authentication = getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (CustomException e) {
            response.setStatus(e.getErrorCode().getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(ErrorCode.INVALID_TOKEN.getMessage());
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String accessToken) {
        Long userId = jwtProvider.getUserIdByAccessToken(accessToken);
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER));
        CustomUserDetails customUser = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
    }
}
