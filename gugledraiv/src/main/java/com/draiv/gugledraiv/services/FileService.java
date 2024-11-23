package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.draiv.gugledraiv.repositories.*;
import com.draiv.gugledraiv.dto.FileDTO;
import com.draiv.gugledraiv.entities.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public List<FileDTO> getFiles(String token, String systemId, String path) {

        Users user = userRepository.findByToken(token);

        if (user == null) {
            throw new NoSuchElementException("No se encontro el usuario");
        }

        List<File> files;
        if (path != null && !path.isEmpty()) {
            files = fileRepository.findByUser_UserIdAndFilePath(user.getUserId(), path);
        } else {
            files = fileRepository.findByUser_UserId(user.getUserId());
        }

        return files.stream().map(file -> new FileDTO(
                file.getId(),
                file.getIsFolder(),
                file.getFilePath(),
                file.getFileExt(),
                file.getFileName(),
                file.getMimeType(),
                file.getContent(),
                file.getIsPublic(),
                file.getFileURL())).collect(Collectors.toList());
    }

    public FileDTO getFileById(Long fileId, String token) {

        Users user = userRepository.findByToken(token);

        if (user == null) {
            throw new NoSuchElementException("No se encontro el usuario");
        }

        File file = fileRepository.findByUser_UserIdAndId(user.getUserId(), fileId);

        FileDTO fileDTO = mapToFileDTO(file);

        return fileDTO;
    }

    private FileDTO mapToFileDTO(File file) {
        FileDTO dto = new FileDTO();
        dto.setId(file.getId());
        dto.setIsFolder(file.getIsFolder());
        dto.setFilePath(file.getFilePath());
        dto.setFileExt(file.getFileExt());
        dto.setFileName(file.getFileName());
        dto.setMimeType(file.getIsFolder() ? null : file.getMimeType());
        dto.setContent(file.getIsFolder() ? null : file.getContent()); 
        dto.setIsPublic(file.getIsPublic());
        dto.setFileURL(file.getIsPublic() ? file.getFileURL() : null);
        return dto;
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    // Generar UUID
    public String generateFileHash() {
        return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }

    // Generar URL pública con fileHash
    public String generatePublicUrl(String fileHash) {
        String baseUrl = "https://poo2024.unsada.edu.ar/draiv/";
        return baseUrl + fileHash;
    }

    // Guardar archivo con fileHash y URL pública
    public File saveFileWithHash(File file) {
        String fileHash = generateFileHash();
        file.setFileHash(fileHash);
        String publicUrl = generatePublicUrl(fileHash);
        file.setFileURL(publicUrl);
        return fileRepository.save(file);
    }

    public Resource getFileByHash(String fileHash) {
        try {
            Path filePath = Paths.get("http://localhost:8082/files/", fileHash);
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

    public Map<String, String> createFileOrFolder(
            String systemId,
            boolean isFolder,
            String filePath,
            String fileExt,
            String fileName,
            String mimeType,
            String content,
            boolean isPublic) {
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

            File savedFile = fileRepository.save(file);
            response.put("fileId", String.valueOf(savedFile.getId()));

            if (isPublic) {
                response.put("fileURL", "https://poo2024.unsada.edu.ar/draiv/" + savedFile.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    // public boolean deleteFileOrFolder(String fileId, String systemId) {
    //     Long id;
    //     try {
    //         id = Long.parseLong(fileId);
    //     } catch (NumberFormatException e) {
    //         return false;
    //     }

    //     Optional<Folder> folderOptional = folderRepository.findById(id);
    //     if (folderOptional.isPresent()) {
    //         Folder folder = folderOptional.get();
    //         folderRepository.delete(folder);
    //         return true;
    //     }

    //     Optional<File> fileOptional = fileRepository.findById(id);
    //     if (fileOptional.isPresent()) {
    //         File file = fileOptional.get();
    //         if (!file.getSystemId().equals(systemId)) {
    //             return false;
    //         }
    //         fileRepository.delete(file);
    //         return true;
    //     }

    //     return false;
    // }
}
