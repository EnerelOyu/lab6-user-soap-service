package com.example.demo.model;

//Authentication serviceiin dotroh useriin medeellig hadhalna
public class AuthUser {
	private String username;
	private String password;
	private String token;
	// constructors
	public AuthUser() {
		
	}
	public AuthUser(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
	}
	
	//getter functions
	public String getUsername() {
        return username;
    }
	public String getPassword() {
        return password;
    }
	public String getToken() {
		return token;
	}
	
    //setter functions
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setToken(String token) {
    	this.token = token;
    }

}
