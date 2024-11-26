package com.draiv.gugledraiv.entities;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "systemId")
    private String systemId;

    @Column (name = "isFolder", nullable = false)
    public Boolean isFolder;

    @Column (name = "filePath", nullable = false)
    private String filePath;

    @Column (name = "fileExt")
    private String fileExt;

    @Column (name = "fileName", nullable = false)
    private String fileName;

    @Column (name = "mimeType")
    private String mimeType;

    @Lob
    @Column (name = "content")
    private String content;

    @Column(name = "fileHash", unique = true)
    private String fileHash;

    @Column (name = "uploadDate", nullable = false)
    private LocalDateTime uploadDate;

    @Column (name = "isPublic", nullable = false)
    private Boolean isPublic;

    @Column(name = "fileUrl")
    private String fileURL;

    //Relacion recursiva para carpetas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folderId")
    private File folder;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> children;

    
    //Relaacion con Users
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public List<File> getChildren() {
        return children;
    }
    
    public void setChildren(List<File> children) {
        this.children = children;
    }
}
