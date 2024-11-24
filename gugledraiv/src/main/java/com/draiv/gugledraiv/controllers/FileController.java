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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.draiv.gugledraiv.entities.File;
import com.draiv.gugledraiv.exceptions.BadRequestException;
import com.draiv.gugledraiv.interfaces.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/draiv")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private IFileService fileService;

    @GetMapping({"/files", "/"})
    public ResponseEntity<List<File>> getAllFiles(@RequestParam String token, @RequestParam String systemId, @RequestParam String path) {
       try {
            List<File> files = fileService.getAllFiles(token, systemId, path);
            return new ResponseEntity<> (files, HttpStatus.OK);
       } catch (Exception e) {
            logger.error("Error fetching files", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
       
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id, @RequestParam String token, @RequestParam String systemId) {
        try {
            File file = fileService.GetFileById(id);
            if(file == null){
                throw new BadRequestException("No existe el path indicado");
            }
            return new ResponseEntity<>(file, HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.warn("File not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error fetching file by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
        }
    }
    // Endpoint para obtener el enlace público de un archivo
    @GetMapping("/{fileHash}")
    public ResponseEntity<?> getFilePublicUrl(@PathVariable String fileHash) {
        try {
            Resource fileResource = fileService.getFileByHash(fileHash); 
            if (fileResource != null) {
                String publicUrl = fileService.generatePublicUrl(fileHash);
                return ResponseEntity.ok(publicUrl);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error generando url publica para file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar URL pública");
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
            logger.error("Error al descargar file", e);
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
            if (!fileService.isAuthenticated(token)) {
                logger.warn("Intento de acceso autorizado con token: {}",token);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Acceso no autorizado"));
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
            logger.error("Error al crear file or folder", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<Map<String, String>> deleteFileOrFolder(
            @PathVariable String fileId,
            @RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String systemId = request.get("systemId");

            if (!fileService.isAuthenticated(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "message", "No autenticado."
                ));
            }

            boolean deleted = fileService.deleteFileOrFolder(fileId, systemId);
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
   





