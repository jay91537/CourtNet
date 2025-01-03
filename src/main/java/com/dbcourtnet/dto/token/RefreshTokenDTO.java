package com.dbcourtnet.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDTO{

    private String refreshToken;

    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
