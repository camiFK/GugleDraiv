package com.draiv.gugledraiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.draiv.gugledraiv.entities.*;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

}
