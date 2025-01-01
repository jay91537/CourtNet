package com.dbcourtnet.dto.logindto;

import com.dbcourtnet.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private Long userId;

    public LoginResponseDTO(User user) {
        this.userId=user.getId();
    }
}
