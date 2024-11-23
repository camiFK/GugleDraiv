package com.draiv.gugledraiv.services;

import org.springframework.stereotype.Service;

import com.draiv.gugledraiv.entities.Users;
import com.draiv.gugledraiv.repositories.UserRepository;

@Service
public class UserService {

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

}
