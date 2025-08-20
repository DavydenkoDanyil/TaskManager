package com.aufgabe.danyil.test.dto.response;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private Long userId; // лучше Long, чем String

    public AuthResponse(String jwt, String email, Long id) {
        this.token = jwt;
        this.email = email;
        this.userId = id;
    }
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
}
