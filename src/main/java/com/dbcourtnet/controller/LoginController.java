package com.dbcourtnet.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 로그이 되기 전 home 화면 (세션 로그인)
    @GetMapping(value = {"/home"})
    public String home( HttpServletRequest request, @SessionAttribute(name = SessionConst.sessionId, required = false) Long userId, Model model) {

        if(userId == null) {
            return "home";
        }
        Optional<User> loginUser = loginService.getLoginUserById(userId);

        if(loginUser!=null) {
            model.addAttribute("username", loginUser.get().getUsername());
        }

        return "home";
    }

    // 회원가입 화면
    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinRequest", new JoinRequestDTO());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequestDTO joinRequest, BindingResult bindingResult) {

        if(loginService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }

        if(bindingResult.hasErrors()) {
            return "join";
        }

        loginService.join(joinRequest);

        return "redirect:/home";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "login";
    }

    // 최초 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest, HttpServletRequest request) throws Exception {

        User user = loginService.login(loginRequest);

        if(user == null) {
            throw new Exception("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        // 서버에 세션 생성 or 기존 세션 반환
        HttpSession session = request.getSession();
        // 내부 저장소에 <"LOGIN_USER", userId> 형태로 담는다
        session.setAttribute(SessionConst.sessionId, user.getId());
        // 만료시간 설정
        session.setMaxInactiveInterval(60);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, @SessionAttribute(name = SessionConst.sessionId, required = false) Long userId,HttpServletResponse response) {

        if(userId == null) {
            return "home";
        }

        request.getSession().invalidate();

        return "redirect:/home";
    }



}
