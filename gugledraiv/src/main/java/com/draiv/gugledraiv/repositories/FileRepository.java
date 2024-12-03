package com.draiv.gugledraiv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; 

import com.draiv.gugledraiv.entities.*;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFolder(File folder);

    File findByFileHash(String fileHash); 

    List<File> findByUser_UserId(String userId);

    File findByUser_UserIdAndId(String userId, Long id);

    List<File> findByUser_UserIdAndFilePath(String userId, String filePath);

    List<File> findBySystemId(String systemId);
}
