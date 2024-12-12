package com.draiv.gugledraiv.dto;

public class UserDTO {

    private String userId;
    private String token;
    private Integer expiresIn;
    
    public UserDTO(String userId, String token, Integer expiresIn) {
        this.userId = userId;
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String setToken(String token) {
        return this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
