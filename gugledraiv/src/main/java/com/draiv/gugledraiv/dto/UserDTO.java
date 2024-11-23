package com.draiv.gugledraiv.dto;

public class UserDTO {

    private Long id;
    private String userId;
    private String token;
    private Integer expiresIn;
    
    public UserDTO(Long id, String userId, String token) {
        this.id = id;
        this.userId = userId;
        this.token = token;
    }
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Long setId(Long id) {
        return this.id = id;
    }

    public void setUserName(String userId) {
        this.userId = userId;
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
    
}
