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
    @Column (name = "filePath", nullable = false)
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

    @Column(name = "fileUrl")
    private String fileURL;

    //Relacion con Folder
    @ManyToOne
    @JoinColumn(name = "folderId")
    private Folder folder;

    //Relaacion con Users
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Boolean getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
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
