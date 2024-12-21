package com.dbcourtnet.login;

import com.dbcourtnet.login.dto.JoinRequestDTO;
import com.dbcourtnet.login.dto.LoginRequestDTO;
import com.dbcourtnet.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 로그이 되기 전 home 화면
    @GetMapping(value = {"/home"})
    public String home(Model model) {



        return "home";
    }

    // 로그인 된 후 home 화면
    @GetMapping(value = {"/home2"})
    public String home2(Model model) {



        return "home2";
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
    public String login(@ModelAttribute LoginRequestDTO loginRequest) throws Exception {

        User user = loginService.login(loginRequest);

        if(user == null) {
            throw new Exception("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        return "redirect:/home2";
    }



}
