package com.draiv.gugledraiv.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.draiv.gugledraiv.dto.UserDTO;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<Users> allUsers = userRepository.findAll();

        List<UserDTO> userDTOs = allUsers.stream()
                    .map(u -> new UserDTO(u.getUserId(), u.getUserName(), u.getToken()))
                    .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userRequest) {  
        Users user = new Users();
        user.setUserName(userRequest.getUserName());
        user.setToken(userRequest.getToken());

        Users savedUser = userRepository.save(user);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuario guardado con ID: " + savedUser.getUserId());
    } 
} 
