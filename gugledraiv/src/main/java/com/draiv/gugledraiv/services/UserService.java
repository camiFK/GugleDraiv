package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.draiv.gugledraiv.entities.Users;
import com.draiv.gugledraiv.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAuthenticated(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        if (userRepository.findByToken(token) == null) {
            return false;
        }
        return true;
    }

    public boolean deleteUserByToken (String token) {
        if(token == null || token.isEmpty()){
            throw new IllegalArgumentException("El token no puede ser nulo o vacío.");
        }

        User user = userRepository.findByToken(token);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
