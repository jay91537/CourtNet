package com.dbcourtnet.login;

import com.dbcourtnet.login.dto.JoinRequestDTO;
import com.dbcourtnet.login.dto.LoginRequestDTO;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.login.session.SessionManager;
import com.dbcourtnet.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final SessionManager sessionManager;

    // 로그이 되기 전 home 화면 (세션 로그인)
    @GetMapping(value = {"/home"})
    public String home( HttpServletRequest request , Model model) {

        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }


        Long userId = (Long)session.getAttribute(SessionConst.sessionId);
        // Long userId = sessionManager.getSession(request);
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

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest, HttpServletRequest request,HttpServletResponse response) throws Exception {

        User user = loginService.login(loginRequest);

        if(user == null) {
            throw new Exception("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.sessionId, user.getId());

        // 올바른 로그인이 진행 될 경우, 세션을 생성한다.
        // sessionManager.createSession((user.getId()), response);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        sessionManager.sessionExpire(request);

        // 2. 브라우저의 쿠키도 삭제
        Cookie cookie = new Cookie(SessionConst.sessionId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/home";
    }



}
