package com.dbcourtnet.login.dto;


import com.dbcourtnet.user.Gender;
import com.dbcourtnet.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {

    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "유저 이름을 입력해주세요.")
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
