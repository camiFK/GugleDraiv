package com.draiv.gugledraiv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.draiv.gugledraiv.entities.Users;
import com.draiv.gugledraiv.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTHORIZE_URL = "https://poo-dev.unsada.edu.ar:8088/cuentas/API/isTokenValid";
     
    public boolean isAuthenticated(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        
        try {
            // Configurar headers con el token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                AUTHORIZE_URL, HttpMethod.GET, entity, String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return true;
            }
    
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return false;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    public boolean deleteUserToken(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Falta el parametro userId.");
        }  
        Users user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalStateException("No se encontro el usuario con el Id: " + userId);
        } else {
            user.setToken(null);
            userRepository.save(user);
            return true;
        }
    }
    
}
