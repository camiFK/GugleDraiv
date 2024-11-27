package com.draiv.gugledraiv.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.draiv.gugledraiv.dto.FileDTO;
import com.draiv.gugledraiv.dto.FileRequest;
import com.draiv.gugledraiv.dto.FileResponse;
import com.draiv.gugledraiv.entities.File;
import com.draiv.gugledraiv.entities.Users;
import com.draiv.gugledraiv.repositories.FileRepository;
import com.draiv.gugledraiv.repositories.UserRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
            if (files == null) {
                throw new NoSuchElementException("No se encontraron archivos en el path indicado.");
            }
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
                file.getFileURL(),
                file.getFileHash())).collect(Collectors.toList());
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

    public File getFileByFileHash(String fileHash){
        return fileRepository.findByFileHash(fileHash);
    }

    public FileResponse createFileOrFolder(FileRequest fileRequest) {
        Users user = userRepository.findByToken(fileRequest.getToken());
        if (user == null) {
            throw new IllegalArgumentException("Token inválido o usuario no encontrado.");
        }

        boolean isValid = isValidFileOrFolder(fileRequest);

        if (!isValid) {
            throw new IllegalArgumentException("El archivo/carpeta no es valido. Faltan parametros.");
        }

        File file = new File();
        file.setSystemId(fileRequest.getSystemId());
        file.setUser(user);
        file.setIsFolder(fileRequest.getIsFolder());
        file.setFilePath(fileRequest.getFilePath());
        file.setFileName(fileRequest.getFileName());
        file.setFileExt(fileRequest.getIsFolder() ? null : fileRequest.getFileExt());
        file.setMimeType(fileRequest.getIsFolder() ? null : fileRequest.getMimeType());
        file.setIsPublic(fileRequest.getIsPublic());
        file.setUploadDate(LocalDateTime.now());

        // if (!fileRequest.getIsFolder() && fileRequest.getFolderId() != null) {
        //     File folder = fileRepository.findById(fileRequest.getFolderId())
        //             .orElseThrow(() -> new IllegalArgumentException("Carpeta no encontrada con id: " + fileRequest.getFolderId()));
        //     file.setFolder(folder); 
        // }

        if (!fileRequest.getIsFolder() && fileRequest.getContent() != null) {

            String base64Content = fileRequest.getContent();
            file.setContent(base64Content);
             
            byte[] contentBytes = Base64.getDecoder().decode(base64Content);
            String fileHash = generateFileHash(contentBytes); 
            file.setFileHash(fileHash);

        } else {
            file.setContent(null);
            file.setFileHash(null);
        }     

        if (!file.getIsFolder() && fileRequest.getIsPublic()) {
            String fileURL = generateFileURL(fileRequest);
            file.setFileURL(fileURL);
        }

        file = fileRepository.save(file);

        FileResponse response = new FileResponse();
        response.setFileId(file.getId().toString());
        response.setFileURL(file.getIsPublic() ? file.getFileURL() : null);

        return response;
    }

    private boolean isValidFileOrFolder(FileRequest fileRequest) {
        if (fileRequest == null) {
            return false;
        }

        if (fileRequest.getFileName() == null || fileRequest.getFileName().trim().isEmpty()) {
            return false;
        }

        if (fileRequest.getIsFolder() == null) {
            return false;
        }

        if (fileRequest.getFilePath() == null || fileRequest.getFilePath().trim().isEmpty()) {
            return false;
        }

        if (fileRequest.getIsPublic() == null) {
            return false;
        }

        return true;
    }

    private String generateFileHash(byte[] contentBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(contentBytes);
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash del archivo", e);
        }
    }

    private String generateFileURL(FileRequest file) {
        return "http://localhost:8082/files/" + file.getFileName();
    }

    public boolean deleteFileOrFolder(Long fileId, String systemId) {

        Optional<File> toDeleteFile = fileRepository.findById(fileId);
        if (toDeleteFile.isPresent()) {
            File file = toDeleteFile.get();

            if (file.getIsFolder()) {
                for (File child : file.getChildren()) {
                    deleteFileOrFolder(child.getId(), systemId); // Llamada recursiva
                }
            }

            fileRepository.delete(file);
            return true;
        }

        return false;
    }
}
