package com.dbcourtnet.dto.logindto;

import com.dbcourtnet.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private Long userId;
    private String token;
    private String username;

    public LoginResponseDTO(String token,User user)
    {
        this.userId = user.getId();
        this.token = token;
        this.username = user.getUsername();
    }
}
