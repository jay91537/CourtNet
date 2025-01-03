package com.dbcourtnet.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    private String accessToken;
    private String refreshToken;

    public TokenDTO(String newAccessToken, String newRefreshToken) {
        this.accessToken = newAccessToken;
        this.refreshToken = newRefreshToken;
    }
}
