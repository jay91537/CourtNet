package com.dbcourtnet.controller;

import com.dbcourtnet.dto.userdto.UserResponseDTO;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.user.User;
import com.dbcourtnet.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userInfo")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> myInfo(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Optional<User> user = userService.findById(userId);

        return ResponseEntity.ok(new UserResponseDTO(
                user
        ));
    }



}
