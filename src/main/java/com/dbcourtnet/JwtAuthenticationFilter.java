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

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/auth")||requestURI.startsWith("/hc")||requestURI.startsWith("/env")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 유효성 검증
        if (token != null) {
            if(jwtTokenProvider.validateToken(token)) {
                // 토큰에서 사용자 Id 추출
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                // 추출한 정보를 request 객체에 저장
                request.setAttribute("userId", userId);
            }
            else {
                // 토큰이 만료되었거나, 허용되지 않은 토큰일 경우
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
            }
        } else {
            // 토큰이 존재하지 않을 경우
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
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
