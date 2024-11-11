package com.draiv.gugledraiv.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.draiv.gugledraiv.entities.*;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    // Buscar archivos fuera carpetas pertenecientes a un usuario específico (userId);   
    @Query(value = "SELECT * FROM file f WHERE f.folder_id IS NULL AND f.user_id = :userId", nativeQuery = true)
    List<File> findByFolderIsNullAndFolderUserId(@Param("userId") Long userId);

    // Buscar carpeta por userId y ruta;
    @Query(value = "SELECT f FROM Folder f WHERE f.user.userId = :userId AND f.path = :path")
    Optional<Folder> findByUserIdAndPath(@Param("userId") String userId, @Param("path") String path);
}

