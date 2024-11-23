package com.draiv.gugledraiv.dto;

public class UserDTO {

    private Long userId;
    private String token;
    private Integer expiresIn;
    
    public UserDTO(Long userId, String token, Integer expiresIn) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
