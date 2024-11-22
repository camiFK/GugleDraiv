package com.draiv.gugledraiv.repositories;
import com.draiv.gugledraiv.entities.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

} 