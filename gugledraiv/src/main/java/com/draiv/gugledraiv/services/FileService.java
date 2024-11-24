package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.draiv.gugledraiv.repositories.*;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.interfaces.IFileService;

import java.util.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@Service
public class FileService implements IFileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;
    @Override
    public List<File> getAllFiles(String token, String systemId, String path) {
        return fileRepository.findAllBySystemIdAndPath(systemId, path);
    }

    @Override
    public File GetFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
        /*
         * .orElseThrow(() -> new
         * NoSuchElementException("No se encontro el archivo con el ID:  " + id));
         */
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    // public File getAllFilesByUserId(Long userId) {
    // return fileRepository.findById(userId).get();
    // }

    // Generar UUID
    public String generateFileHash() {
        return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }

    @Override// Generar URL pública con fileHash
    public String generatePublicUrl(String fileHash) {
        String baseUrl = "https://poo2024.unsada.edu.ar/draiv/";
        return baseUrl + fileHash;
    }

    // Guardar archivo con fileHash y URL pública
    public File saveFileWithHash(File file) {
        if(file.getFileHash() == null) {
            String fileHash = generateFileHash();
            file.setFileHash(fileHash);
        }
        String publicUrl = generatePublicUrl(file.getFileHash());
        file.setFileURL(publicUrl);
        return fileRepository.save(file);
    }
    private String storagePath;

    @Override
    public Resource getFileByHash(String fileHash) {
        try {
            Path filePath = Paths.get(storagePath, fileHash);
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
    @Override
    public boolean isAuthenticated(String usertoken) {
        return "token_valido".equals(usertoken);
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
            file.setContent(content);
            file.setIsPublic(isPublic);

            File savedFile = saveFileWithHash(file);
            response.put("fileId", String.valueOf(savedFile.getId()));

            if (isPublic) {
                response.put("fileURL", "https://poo2024.unsada.edu.ar/draiv/" + savedFile.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public boolean deleteFileOrFolder(String fileId, String systemId) {
        Long id;
        try {
            id = Long.parseLong(fileId);
        } catch (NumberFormatException e) {
            return false;
        }

        Optional<Folder> folderOptional = folderRepository.findById(id);
        if (folderOptional.isPresent()) {
            Folder folder = folderOptional.get();
            folderRepository.delete(folder);
            return true;
        }

        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            if (!file.getSystemId().equals(systemId)) {
                return false;
            }
            fileRepository.delete(file);
            return true;
        }

        return false;
    }
}
