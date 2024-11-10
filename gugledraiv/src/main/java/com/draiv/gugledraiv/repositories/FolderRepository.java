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

    // List<File> findByFolderIsNullAndFolderUserId(String userId);   
    @Query(value = "WITH RECURSIVE carpetas_jerarquia AS (" +
                   "  SELECT id, nombre, carpeta_padre_id FROM carpeta WHERE carpeta_padre_id IS NULL " +
                   "  UNION ALL " +
                   "  SELECT c.id, c.nombre, c.carpeta_padre_id FROM carpeta c " +
                   "  INNER JOIN carpetas_jerarquia cj ON c.carpeta_padre_id = cj.id" +
                   ") " +
                   "SELECT * FROM carpetas_jerarquia", nativeQuery = true)
    List<Folder> findCarpetasJerarquia();
    
    @Query("SELECT d FROM Documento d WHERE d.carpeta.id = :carpetaId")
    List<File> findDocumentosByFolderId(@Param("carpetaId") Long carpetaId);
}
    // List<File> findByFolder(Folder folder);

    //@Query("""
            
            //""";)
    
    // Optional<Folder> findByUserIdAndPath(String userId, String path);

