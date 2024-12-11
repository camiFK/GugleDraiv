package com.draiv.gugledraiv.dto;

public class FileDTO {
    private Long id;
    private Boolean isFolder;
    private String filePath;
    private String fileExt;
    private String fileName;
    private String mimeType;
    private String content;
    private Boolean isPublic;
    private String fileURL;
    private String fileHash;
    private Long folderId;

    public FileDTO() {
        super();
    }

    public FileDTO(Long id, Boolean isFolder, String filePath, String fileExt, String fileName, String mimeType, String content, Boolean isPublic, String fileURL, String fileHash, Long folderId) {
        this.id = id;
        this.isFolder = isFolder;
        this.filePath = filePath;
        this.fileExt = fileExt;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.content = content;
        this.isPublic = isPublic;
        this.fileURL = fileURL;
        this.fileHash = fileHash;
        this.folderId = folderId;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getFolderId(){
        return folderId;
    }
    public void setFolderId(Long folderId){
        this.folderId = folderId;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + id +
                ", isFolder=" + isFolder +
                ", filePath='" + filePath + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", isPublic=" + isPublic +
                ", fileURL='" + fileURL + '\'' +
                ", folderId='" + folderId + '\'' +
                '}';
    }
}
