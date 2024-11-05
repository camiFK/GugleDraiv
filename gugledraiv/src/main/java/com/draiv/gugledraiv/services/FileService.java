package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.draiv.gugledraiv.repositories.*;
import com.draiv.gugledraiv.entities.*;

import java.util.Optional;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    private FolderRepository folderRepository;

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public Optional<File> getAllFilesByUserId(Long userId) {
        return fileRepository.findById(userId);
    }
    
    // public Optional<Folder> getFolderByPath(String userId, String path) {
    //     return folderRepository.findByUserIdAndPath(userId, path);
    // }
    
    // public List<File> getFilesByFolder(Folder folder) {
    //     return fileRepository.findByFolder(folder);
    // }

    // Agregar metodos
}
