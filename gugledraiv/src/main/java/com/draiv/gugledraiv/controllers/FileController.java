package com.draiv.gugledraiv.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.draiv.gugledraiv.dto.FileRequest;
import com.draiv.gugledraiv.dto.FileResponse;
import com.draiv.gugledraiv.entities.File;
import com.draiv.gugledraiv.services.FileService;
import com.draiv.gugledraiv.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
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
            if (!userService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
            }

            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Token y systemId son parametros obligatorios.");
            }

            List<FileDTO> files = fileService.getFiles(token, systemId, path);

            return ResponseEntity.status(HttpStatus.OK).body(files);

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
            if (!userService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
            }

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el path indicado");
        }
    }

    // Endpoint para descargar un archivo
    @GetMapping("/download/{fileHash}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileHash) {
        try {
            File file = fileService.getFileByFileHash(fileHash);

            if (file == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "No existe el archivo solicitado."));
            }

            if(file.getIsFolder()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "El archivo solicitado es una carpeta, no puede descargarse"));
            }

            byte[] decodedFileContent = Base64.getDecoder().decode(file.getContent());
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + file.getFileName());

            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(decodedFileContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)  
                .body(decodedFileContent); 

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al procesar la solicitud."));
        }
    }

    @PostMapping("/files")
    public ResponseEntity<?> createFileOrFolder(@RequestBody FileRequest fileRequest) {
        try {
            String token = fileRequest.getToken();
            String systemId = fileRequest.getSystemId();

            if (!userService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
            }

            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token y systemId son parametros obligatorios.");
            }

            FileResponse response = fileService.createFileOrFolder(fileRequest);

            if (response == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el archivo/carpeta.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el archivo o carpeta.");
        }
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<?> deleteFileOrFolder(
            @PathVariable String fileId,
            @RequestParam String token,
            @RequestParam String systemId) {
        try {

            if (!userService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado.");
            }

            if (token == null || token.isEmpty() || systemId == null || systemId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token y systemId son obligatorios.");
            }

            boolean deleted = fileService.deleteFileOrFolder(Long.parseLong(fileId), systemId);

            if (deleted) {
                return ResponseEntity.ok(Map.of(
                        "fileId", fileId,
                        "message", "Archivo/Carpeta eliminado correctamente."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "message", "Error al eliminar archivo/carpeta."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al procesar la solicitud."));
        }
    }
}
