package com.draiv.gugledraiv.interfaces;

import com.draiv.gugledraiv.entities.File;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;


public interface IFileService {
    public List<File> getAllFiles(String token, String systemId, String path);
    public File GetFileById(Long id);
    public Resource getFileByHash(String hash);  // Cambiar a Resource para coincidir con la implementación
    public String generatePublicUrl(String hash);
    public boolean isAuthenticated(String userToken);
    public Map<String, String> createFileOrFolder(String systemId, boolean isFolder, String filePath, String fileExt,
    String fileName, String mimeType, String content, boolean isPublic);
    public boolean deleteFileOrFolder(String fileId, String systemId);
}

