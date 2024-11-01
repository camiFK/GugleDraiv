package com.draiv.gugledraiv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.time.LocalDateTime;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String fileHash;
    private Long size;
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    // Agregar Getters y setters
}
