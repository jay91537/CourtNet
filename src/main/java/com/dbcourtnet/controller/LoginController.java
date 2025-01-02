package com.dbcourtnet.controller;

import com.dbcourtnet.dto.logindto.LoginResponseDTO;
import com.dbcourtnet.jwt.JwtTokenProvider;
import com.dbcourtnet.login.LoginService;
import com.dbcourtnet.dto.logindto.JoinRequestDTO;
import com.dbcourtnet.dto.logindto.LoginRequestDTO;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequestDTO joinRequest) {
        if(loginService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        loginService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
        User user = loginService.login(loginRequest);

        if(user == null) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(token ,user));
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
