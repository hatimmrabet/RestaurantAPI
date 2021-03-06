package com.lapetitesyrienne.api.models.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String id, String username, String email, String role) {
        this.id = id;
        this.email = email;
        this.token = accessToken;
        this.role = role;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
}