package com.draiv.gugledraiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.draiv.gugledraiv.entities.*;

public interface FileRepository extends JpaRepository<File, Long> {
    // Agregar metodos para SQL
}