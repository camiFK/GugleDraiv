package com.draiv.gugledraiv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.draiv.gugledraiv.entities.*;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
<<<<<<< HEAD
    
=======
    // List<File> findByUserId(Long userId);
>>>>>>> f8a2a241963d12607019e27bf212e78abd11c409
}