package com.draiv.gugledraiv.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;

    @Column(name = "userId")
    private String userId;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "expiresIn")
    private Integer expiresIn;

    
    //Relaciom con File
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<File> files;
    
    public Users() {
    }

    public Long getUserId() {
        return idUser;
    }

    public void setUserId(Long idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<File> getFiles() {
        return files;
    }
    
    public void setFiles(List<File> files) {
        this.files = files;
    }
    
    public String getUserName() {
        return userId;
    }
    
    public void setUserName(String userId) {
        this.userId = userId;
    }
    
    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
