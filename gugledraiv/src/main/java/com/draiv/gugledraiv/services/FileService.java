package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.draiv.gugledraiv.repositories.*;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.interfaces.IFileService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService implements IFileService {

    @Autowired
    private FileRepository fileRepository;

    public List<File> GetAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public File GetFileById(Long id) {
        return fileRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("No se encontro el archivo con el ID:  " + id));
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    // public File getAllFilesByUserId(Long userId) {
    //     return fileRepository.findById(userId).get();
    // }

    // Agregar metodos

    public Resource getFileByHash(String fileHash) {
        try {
            Path filePath = Paths.get("ruta/del/archivo", fileHash);
            Resource file = new UrlResource(filePath.toUri());
            
            if (Files.exists(filePath) && file.isReadable()) {
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public boolean isAuthenticated(String token) {
        return "token_valido".equals(token);
    }

    public Map<String, String> createFileOrFolder(String systemId, boolean isFolder, String filePath, String fileExt,
    String fileName, String mimeType, String content, boolean isPublic) {
Map<String, String> response = new HashMap<>();

try {
File file = new File();
file.setSystemId(systemId);
file.setIsFolder(isFolder);
file.setFilePath(filePath);
file.setFileName(fileExt);
file.setFileName(fileName);
file.setMimeType(mimeType);
file.setContent(content); // Ajusta según cómo almacenes el contenido
file.setIsPublic(isPublic);

File savedFile = fileRepository.save(file);
response.put("fileId", String.valueOf(savedFile.getId()));

if (isPublic) {
response.put("fileURL", "https://tu_dominio.com/files/" + savedFile.getId());
}

} catch (Exception e) {
e.printStackTrace();
}

return response;
}

}
