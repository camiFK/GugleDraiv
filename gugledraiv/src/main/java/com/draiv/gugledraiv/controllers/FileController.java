package com.draiv.gugledraiv.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.draiv.gugledraiv.dto.FileDTO;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.repositories.UserRepository;
import com.draiv.gugledraiv.services.FileService;
import com.draiv.gugledraiv.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {
    @Autowired
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping({ "/files", "/" })
    public ResponseEntity<?> getFiles(
            @RequestParam String token,
            @RequestParam String systemId,
            @RequestParam(required = false) String path) {
        try {

            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token y systemId son parametros obligatorios.");
            }

            List<FileDTO> files = fileService.getFiles(token, systemId, path);

            if (files == null || files.isEmpty()) {       
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el path indicado.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(files);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<?> getFileById(
            @PathVariable Long fileId,
            @RequestParam String token,
            @RequestParam String systemId) {
        try {
            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token y systemId son obligatorios.");
            }

            FileDTO fileDTO = fileService.getFileById(fileId, token);

            if (fileDTO == null) {          
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el archivo/carpeta indicado.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(fileDTO);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @GetMapping("/{fileHash}")
    public ResponseEntity<?> getFilePublicUrl(@PathVariable String fileHash) {
        Resource fileResource = fileService.getFileByHash(fileHash); 
        if (fileResource != null) {
            String publicUrl = fileService.generatePublicUrl(fileHash);
            return ResponseEntity.ok(publicUrl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado.");
        }
    }

    // Endpoint para descargar un archivo
    @GetMapping("/download/{fileHash}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileHash) {
        try {
            Resource file = fileService.getFileByHash(fileHash);
            if (file == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "No existe el archivo solicitado."
                ));
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al procesar la solicitud."
            ));
        }
    }

    // Endpoint para crear un archivo o una carpeta
    @PostMapping("/files")
    public ResponseEntity<Map<String, String>> createFileOrFolder(@RequestBody Map<String, Object> request) {
        try {
            String token = (String) request.get("token");
            if (!userService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String systemId = (String) request.get("systemId");
            boolean isFolder = (Boolean) request.get("isFolder");
            String filePath = (String) request.get("filePath");
            String fileExt = (String) request.get("fileExt");
            String fileName = (String) request.get("fileName");
            String mimeType = (String) request.get("mimeType");
            String content = (String) request.get("content");
            boolean isPublic = (Boolean) request.get("isPublic");

            Map<String, String> response = fileService.createFileOrFolder(systemId, isFolder, filePath, fileExt, fileName, mimeType, content, isPublic);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/files/{fileId}")
    public  ResponseEntity<?> deleteFileOrFolder(
            @PathVariable String fileId,
            @RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String systemId = request.get("systemId");

            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token y systemId son obligatorios.");
            }

            boolean deleted = fileService.deleteFileOrFolder(Long.parseLong(fileId), systemId);
            
            if (deleted) {
                return ResponseEntity.ok(Map.of(
                        "fileId", fileId,
                        "message", "Archivo/Carpeta eliminado correctamente"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "message", "Error al eliminar archivo/carpeta."
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al procesar la solicitud."
            ));
        }
    }
}
   





