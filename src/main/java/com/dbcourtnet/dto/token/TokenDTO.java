package com.dbcourtnet.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    private String accessToken;
    private String refreshToken;
    private String username;

    public TokenDTO(String newAccessToken, String newRefreshToken, String username) {
        this.accessToken = newAccessToken;
        this.refreshToken = newRefreshToken;
        this.username = username;
    }
}
