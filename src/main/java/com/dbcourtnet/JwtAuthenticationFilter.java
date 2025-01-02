package com.dbcourtnet;

import com.dbcourtnet.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOError;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected  void doFilterInternal (HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain filterChain) throws IOException, ServletException {

        // request 객체에서 토큰 추출
        String token = getTokenFromRequest(request);

        // 토큰 유효성 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰에서 사용자 Id 추출
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            // 추출한 정보를 request 객체에 저장
            request.setAttribute("userId", userId);
        }

        // 다음 필터로 request 전달
        filterChain.doFilter(request, response);
    }

    // request 헤더에서 토큰 추출
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
