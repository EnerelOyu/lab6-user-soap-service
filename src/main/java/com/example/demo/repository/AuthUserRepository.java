package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByToken(String token);
}