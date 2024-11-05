package com.draiv.gugledraiv.entities;

import jakarta.persistence.Column;
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

    // @Column(nullable = false)
    private String token;

    private String systemId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // @Column(nullable = false)
    private Boolean isFolder;

    // @Column(nullable = false)
    private String filePath;

    // @Column(nullable = false)
    private String fileExt;

    // @Column(nullable = false)
    private String fileName;

    // @Column(nullable = false)
    private String mimeType;

    // @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String name;

    // @Column(nullable = false)
    private String fileHash;

    // @Column(nullable = false)
    private LocalDateTime uploadDate;

    // @Column(nullable = false)
    private Boolean isPublic;

    // @Column(nullable = false)
    private String fileURL;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    // Agregar Getters y setters
}
