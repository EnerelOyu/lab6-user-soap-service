package com.example.demo.service;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.AuthUser;
import com.example.demo.repository.AuthUserRepository;

@Service
public class AuthService {
	
	private final AuthUserRepository repository;
	
	public AuthService(AuthUserRepository repository) {
		this.repository = repository;
		
	}
	//shine user burtguuleh method, username existing baiwal false butsaana
	
	public boolean registerUser(String username, String password) {
		AuthUser existing = repository.findByUsername(username);
		if(existing != null) {
			return false;
		}
		AuthUser newUser = new AuthUser(username, password, null);
		repository.save(newUser);
		return true;
		
	}
	
	//user newtreh method
	//username password zuw bol token uusgeed return hiine, buruu bol null butsaana
	
	public String loginUser(String username, String password) {
		AuthUser user = repository.findByUsername(username);
		
		if(user == null) {
			return null;
		}
		
		if(!user.getPassword().equals(password)) {
			return null;
		}
		
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		return token;
	}
	
	//token shalgah method
	public boolean validateToken(String token) {
		AuthUser user = repository.findByToken(token);
		return user != null;
	}
}
