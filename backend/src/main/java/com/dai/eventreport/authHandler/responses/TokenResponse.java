package com.dai.eventreport.authHandler.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TokenResponse {
    private String userId;
    private boolean admin;
    private String email;

    public TokenResponse(String userId, String email, boolean admin) {
        this.userId = userId;
        this.email = email;
        this.admin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }
}
