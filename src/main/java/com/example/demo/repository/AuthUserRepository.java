package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.AuthUser;

//useriin medeellig listen hadaglah repository
@Repository
public class AuthUserRepository {
	
	private final List<AuthUser> users = new ArrayList<>();
	//save method
	public void save(AuthUser user) {
		users.add(user);
	}
	
	public AuthUser findByUsername(String username) {
		for(AuthUser user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public AuthUser findByToken(String token) {
		for(AuthUser user : users) {
			if(user.getToken().equals(token)) {
				return user;
			}
		}
		return null;
	}
}
