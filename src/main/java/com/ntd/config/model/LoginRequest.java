package com.ntd.config.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

    // Default constructor
    public LoginRequest() {
    }

    // Parameterized constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
