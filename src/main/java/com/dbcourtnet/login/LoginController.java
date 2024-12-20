package com.dbcourtnet.login;

import com.dbcourtnet.login.dto.JoinRequestDTO;
import com.dbcourtnet.login.dto.LoginRequestDTO;
import com.dbcourtnet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"", "/"})
public class LoginController {

    private final LoginService loginService;

    // 로그이 되기 전 home 화면
    @GetMapping(value = {"", "/"})
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
    public String join(@ModelAttribute JoinRequestDTO joinRequest) throws Exception {

        if(loginService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            throw new Exception("이미 존재하는 아이디 입니다.");
        }

        loginService.join(joinRequest);

        return "/login";
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

        if(user == null){
            throw new Exception("존재하지 않는 유저입니다.");
        }

        return "/home2";
    }



}
