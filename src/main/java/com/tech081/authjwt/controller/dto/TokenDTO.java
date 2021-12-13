package com.tech081.authjwt.controller.dto;

import java.time.LocalDateTime;

public class TokenDTO {

    private String accessToken;
    private String tokenType;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public TokenDTO() {
    }

    public TokenDTO(String token, String tokenType) {
        this.accessToken = token;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
