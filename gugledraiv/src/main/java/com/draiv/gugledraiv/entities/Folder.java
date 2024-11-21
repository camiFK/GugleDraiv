package com.draiv.gugledraiv.entities;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //Relacion uno a muchos con File
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files;
    
    //Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
