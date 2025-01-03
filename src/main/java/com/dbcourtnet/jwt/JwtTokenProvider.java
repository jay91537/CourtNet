package com.dbcourtnet.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

/*
    JwtTokenProvider 는 서버의 시크릿 키, 로그인한 유저의 일부 정보 등을 이용해 토큰을 생성,
    유저가 인증을 위해 서버에 제공한 토큰의 유효성을 검증,
    토큰에서 유저의 Id를 추출하는 역할등을 한다.
*/
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-ms}")
    private long accessTokenValidityInMs;

    @Value("${jwt.refresh-token-validity-in-ms}")
    private long refreshTokenValidityInMs;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long userId, String username) {
        return createToken(userId, username, accessTokenValidityInMs);
    }

    public String createRefreshToken(Long userId, String username) {
        return createToken(userId, username, refreshTokenValidityInMs);
    }

    // 토큰 생성
    public String createToken(Long userId, String username, long validityInMs) {
        // 토큰에 포함될 정보
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);

        // 현재 시간
        Date now = new Date();
        // 만료 시간
        Date validity = new Date(now.getTime() + validityInMs);

        // Jwt 빌드
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key) // 서명
                .compact(); // 토큰 생성
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            // 토큰 파싱 및 검증
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            // 만료 시간 검증
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 유저 Id 추출
    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // 토큰의 payload 에서 정보 가져오기
    }



}
