package com.dbcourtnet.dto.userdto;

import com.dbcourtnet.user.Gender;
import com.dbcourtnet.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class UserResponseDTO {

    private Long userId;
    private String loginId;
    private String password;
    private String username;
    private Gender gender;


    public UserResponseDTO(Optional<User> user) {
        this.userId = user.get().getId();
        this.loginId = user.get().getLoginId();
        this.password = user.get().getPassword();
        this.username = user.get().getUsername();
        this.gender = user.get().getGender();
    }
}
