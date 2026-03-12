package com.example.demo.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.auth.LoginUserRequest;
import com.example.demo.auth.LoginUserResponse;
import com.example.demo.auth.RegisterUserRequest;
import com.example.demo.auth.RegisterUserResponse;
import com.example.demo.auth.ValidateTokenRequest;
import com.example.demo.auth.ValidateTokenResponse;
import com.example.demo.service.AuthService;

@Endpoint
public class AuthEndpoint {
	
   private static final String NAMESPACE_URI = "http://example.com/usersoapservice/auth";
   private final AuthService authService;
   
   public AuthEndpoint(AuthService authService) {
	   this.authService = authService;
   }
   //burtguuleh
   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterUserRequest")
   @ResponsePayload
   public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
	   RegisterUserResponse response = new RegisterUserResponse();
	   
	   boolean success = authService.registerUser(request.getUsername(), request.getPassword());
	   
	   if(success) {
		   response.setSuccess(true);
		   response.setMessage("Хэрэглэгчийн бүртгэл амжилттай.");
		   
	   }else {
		   response.setSuccess(false);
		   response.setMessage("Хэрэглэгчийн нэр аль хэдий нь үүссэн байна.");
	   }
	   
	   return response;
   }
   
   //newtreh
   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginUserRequest")
   @ResponsePayload
   public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) {
	   LoginUserResponse response = new LoginUserResponse();
	   String token = authService.loginUser(request.getUsername(), request.getPassword());
	   
	   if(token != null) {
		   response.setSuccess(true);
		   response.setMessage("Нэвтрэлт амжилттай.");
		   response.setToken(token);
	   }else {
		   response.setSuccess(false);
		   response.setMessage("Нэвтрэх нэр эсвэл нэвтрэх нууц үг буруу.");
	   }
	   
	   return response;
   }
   
   //token shalgalt
   
   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
   @ResponsePayload
   public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest request) {
	   ValidateTokenResponse response = new ValidateTokenResponse();
	   boolean valid = authService.validateToken(request.getToken());
	   
	   response.setValid(valid);
	   
	   if(valid) {
		   response.setMessage("token хүчинтэй.");
	   }else {
		   response.setMessage("token хүчингүй байна.");
	   }
	   return response;
   }
   
}
