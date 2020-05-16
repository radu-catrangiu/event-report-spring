package com.dai.eventreport.authHandler.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginResponse {
    private String userId;
    private boolean admin;
    private String email;
    private String loginToken;

    public LoginResponse(String userId, String email, String loginToken, boolean admin) {
        this.userId = userId;
        this.admin = admin;
        this.email = email;
        this.loginToken = loginToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public String getLoginToken() {
        return loginToken;
    }
}
