package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.AuthUser;
import com.example.demo.repository.AuthUserRepository;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;

    public AuthService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public boolean registerUser(String username, String password) {
        Optional<AuthUser> existingUser = authUserRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            return false;
        }

        AuthUser user = new AuthUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(null);
        user.setUserId(null);

        authUserRepository.save(user);
        return true;
    }

    public AuthUser loginUser(String username, String password) {
        Optional<AuthUser> userOpt = authUserRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            AuthUser user = userOpt.get();

            if (user.getPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                authUserRepository.save(user);
                return user;
            }
        }

        return null;
    }

    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        Optional<AuthUser> userOpt = authUserRepository.findByToken(token);
        return userOpt.isPresent();
    }

    public AuthUser getUserByToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        Optional<AuthUser> userOpt = authUserRepository.findByToken(token);
        return userOpt.orElse(null);
    }

    public boolean attachUserProfileId(String username, Integer userId) {
    	if (userId == null) {
            return false;
        }
    	
        Optional<AuthUser> userOpt = authUserRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            AuthUser user = userOpt.get();
            user.setUserId(userId);
            authUserRepository.save(user);
            return true;
        }

        return false;
    }
}