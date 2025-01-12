package com.dbcourtnet.controller;

import com.dbcourtnet.dto.token.RefreshTokenDTO;
import com.dbcourtnet.dto.token.TokenDTO;
import com.dbcourtnet.jwt.JwtTokenProvider;
import com.dbcourtnet.jwt.RefreshToken;
import com.dbcourtnet.jwt.RefreshTokenRepository;
import com.dbcourtnet.login.LoginService;
import com.dbcourtnet.dto.logindto.JoinRequestDTO;
import com.dbcourtnet.dto.logindto.LoginRequestDTO;
import com.dbcourtnet.user.User;
import com.dbcourtnet.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequestDTO joinRequest) {
        if(loginService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        loginService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
        User user = loginService.login(loginRequest);

        if(user == null) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername());

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        // db에 기존 토큰 값이 존재하면 기존의 refresh 토큰을 갱신
                        token -> token.updateRefreshToken(refreshToken),
                        // db에 토큰이 없으면 새로운 refresh 토큰을 생성하고 저장
                        () -> refreshTokenRepository.save(new RefreshToken(refreshToken, user.getId()))
                );

        return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
    }

    // 토큰 갱신 요청 처리
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody RefreshTokenDTO refreshToken) {

        // 만료되었거나 위조된 토큰이면 예외 발생
        if(!jwtTokenProvider.validateToken(refreshToken.getRefreshToken())) {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }

        // DB에 저장된 refreshToken 찾기, 토큰 없으면 도난되었거나 이미 사용된 토큰
        RefreshToken savedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰을 찾을 수 없습니다."));

        User user = userService.findById(savedRefreshToken.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getId() ,user.getUsername());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername());

        // DB에 저장된 refresh 토큰을 새로 생성된 토큰으로 교체
        savedRefreshToken.updateRefreshToken(newRefreshToken);

        // 새로운 access 토큰과 refresh 토큰 전달
        return ResponseEntity.ok(new TokenDTO(newAccessToken, newRefreshToken));
    }


    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return ResponseEntity.ok().build();
    }

}
