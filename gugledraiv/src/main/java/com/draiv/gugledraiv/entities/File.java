package com.draiv.gugledraiv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.time.LocalDateTime;

import jakarta.persistence.Table;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(nullable = false)
    @Column (name = "token")
    private String token;

    @Column (name = "systemId")
    private String systemId;

    // @Column(nullable = false)
    @Column (name = "isFolder")
    private Boolean isFolder;

    // @Column(nullable = false)
    @Column (name = "filePath")
    private String filePath;

    // @Column(nullable = false)
    @Column (name = "fileExt")
    private String fileExt;

    // @Column(nullable = false)
    @Column (name = "fileName")
    private String fileName;

    // @Column(nullable = false)
    @Column (name = "mimeType")
    private String mimeType;

    // @Column(nullable = false)
    @Column (name = "content")
    private String content;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "fileHash", nullable = false, unique = true)
    private String fileHash;

    // @Column(nullable = false)
    @Column (name = "uploadDate")
    private LocalDateTime uploadDate;

    // @Column(nullable = false)
    @Column (name = "isPublic")
    private Boolean isPublic;

    @Column(name = "fileUrl", nullable = false)
    private String fileURL;

    //Relacion con Folder
    @ManyToOne
    @JoinColumn(name = "folderId")
    private Folder folder;

    //Relaacion con Users
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;


    public File() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSystemId'");
    }

    public void setIsFolder(boolean isFolder2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIsFolder'");
    }

    public void setMimeType(String mimeType2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMimeType'");
    }

    public void setContent(String content2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setContent'");
    }

    public void setIsPublic(boolean isPublic2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIsPublic'");
    }

    public void setfileHash(String fileHash2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setfileHash'");
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
