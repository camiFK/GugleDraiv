package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.draiv.gugledraiv.repositories.*;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.interfaces.IFileService;
import java.util.List;
import java.util.NoSuchElementException;

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
}
