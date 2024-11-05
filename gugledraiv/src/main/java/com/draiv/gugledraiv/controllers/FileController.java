package com.draiv.gugledraiv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.services.*;

import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{userId}")
    public Optional<File> getAllFiles(@PathVariable("userId") Long userId) {
        return fileService.getAllFilesByUserId(userId);
    }

    @PostMapping
    public File uploadFile(@RequestBody File file) {
        return fileService.saveFile(file);
    }

    // Agregar endpoints
}
