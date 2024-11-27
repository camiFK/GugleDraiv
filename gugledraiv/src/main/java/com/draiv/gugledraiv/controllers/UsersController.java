package com.draiv.gugledraiv.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.draiv.gugledraiv.dto.UserDTO;
import com.draiv.gugledraiv.entities.*;
import com.draiv.gugledraiv.repositories.UserRepository;
import com.draiv.gugledraiv.services.UserService;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<Users> allUsers = userRepository.findAll();

        List<UserDTO> userDTOs = allUsers.stream()
                    .map(u -> new UserDTO(u.getUserId(), u.getToken(), u.getExpiresIn()))
                    .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userRequest) {  
        Users user = new Users();
        user.setUserId(userRequest.getUserId());
        user.setToken(userRequest.getToken());
        user.setExpiresIn(userRequest.getExpiresIn());

        Users savedUser = userRepository.save(user);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuario guardado con ID: " + savedUser.getUserId());
    } 

    @DeleteMapping("/users/logout")
    public ResponseEntity<String> deleteUser(@RequestParam String token) {
        try {
        boolean isDeleted = userService.deleteUserByToken(token);
        if (isDeleted) {
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario.");
        }
    }


} 
