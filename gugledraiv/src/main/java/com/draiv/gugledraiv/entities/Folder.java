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

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files;

    // Agregar Getters y setters
}
