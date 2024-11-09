package com.draiv.gugledraiv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping({"/files", "/"})
    public List<File> getAllFiles() {
        return fileService.GetAllFiles();
    }

    @GetMapping("/files/{id}")
    public File GetFileById(@PathVariable Long id) {
        return fileService.GetFileById(id);
    }
    

    // Agregar endpoints
}
