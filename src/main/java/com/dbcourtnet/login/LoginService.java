package com.dbcourtnet.login;

import com.dbcourtnet.dto.logindto.JoinRequestDTO;
import com.dbcourtnet.dto.logindto.LoginRequestDTO;
import com.dbcourtnet.user.User;
import com.dbcourtnet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    public boolean checkLoginIdDuplicate(String loginId) {
        return loginRepository.existsByLoginId(loginId);
    }


    public void join(JoinRequestDTO joinRequest) {
        userRepository.save(joinRequest.toEntity());
    }


    public User login(LoginRequestDTO loginRequest) {

        Optional<User> optionalUser = userRepository.findByLoginId(loginRequest.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        return user;
    }

    public Optional<User> getLoginUserById(Long userId) {

        if(userId == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return Optional.of(optionalUser.get());
    }
}
