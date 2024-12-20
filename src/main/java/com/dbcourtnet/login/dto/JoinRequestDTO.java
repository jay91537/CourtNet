package com.dbcourtnet.login.dto;


import com.dbcourtnet.user.Gender;
import com.dbcourtnet.user.User;
import lombok.Getter;

@Getter
public class JoinRequestDTO {

    private String loginId;

    private String password;

    private String username;

    private Gender gender;

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .username(this.username)
                .gender(this.gender)
                .build();
    }


}
