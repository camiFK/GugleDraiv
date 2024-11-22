package com.draiv.gugledraiv.dto;

public class UserDTO {

    private Long id;
    private String userName;
    private String token;
    private Integer expiresIn;

    public UserDTO() {
        super();
    }

    public UserDTO(Long id, String userName, String token) {
        this.id = id;
        this.userName = userName;
        this.token = token;
    }
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

    public Long setId(Long id) {
        return this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
