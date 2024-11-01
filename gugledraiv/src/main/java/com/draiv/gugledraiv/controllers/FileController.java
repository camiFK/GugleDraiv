package com.draiv.gugledraiv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.services.*;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @PostMapping
    public File uploadFile(@RequestBody File file) {
        return fileService.saveFile(file);
    }

    // Agregar endpoints
}
