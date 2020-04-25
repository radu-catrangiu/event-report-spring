package com.dai.eventreport.authHandler.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginResponse {
    private boolean admin;
    private String email;
    private String loginToken;


    public LoginResponse(String email, String loginToken, boolean admin) {
        this.admin = admin;
        this.email = email;
        this.loginToken = loginToken;
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
