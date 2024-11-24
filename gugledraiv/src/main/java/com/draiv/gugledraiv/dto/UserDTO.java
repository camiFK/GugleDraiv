package com.draiv.gugledraiv.dto;

public class UserDTO {

    private Long id;
    private String userName;
    private String token;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
