package com.dbcourtnet.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Optional<User> findById(Long userId) {

        if(userId == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        return userRepository.findById(userId);
    }
}
