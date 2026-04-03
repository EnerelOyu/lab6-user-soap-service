package com.example.demo.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.auth.LinkUserProfileRequest;
import com.example.demo.auth.LinkUserProfileResponse;
import com.example.demo.auth.LoginUserRequest;
import com.example.demo.auth.LoginUserResponse;
import com.example.demo.auth.RegisterUserRequest;
import com.example.demo.auth.RegisterUserResponse;
import com.example.demo.auth.ValidateTokenRequest;
import com.example.demo.auth.ValidateTokenResponse;
import com.example.demo.model.AuthUser;
import com.example.demo.service.AuthService;


@Endpoint
public class AuthEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/usersoapservice/auth";
    private final AuthService authService;

    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    //шинэ хэрэглэгч бүртгэх method
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();
        //бүртгэл амжилттай эсэхийг AuthService-ээр шалгах
        boolean success = authService.registerUser(request.getUsername(), request.getPassword());
        //бүртгэлийн үр дүнд тохируулан амжилт эсвэл алдааны мессежийг response-д тохируулах
        if (success) {
            response.setSuccess(true);
            response.setMessage("Хэрэглэгчийн бүртгэл амжилттай.");
        } else {
            response.setSuccess(false);
            response.setMessage("Хэрэглэгчийн нэр аль хэдийн үүссэн байна.");
        }

        return response;
    }

    //хэрэглэгч нэвтрэх method
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginUserRequest")
    @ResponsePayload
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) {
        LoginUserResponse response = new LoginUserResponse();
        //AuthService-ээр хэрэглэгчийн нэр болон нууц үгийг шалгах, амжилттай бол токен болон хэрэглэгчийн ID-г буцаана
        AuthUser user = authService.loginUser(request.getUsername(), request.getPassword());
        //хэрэглэгчийн нэвтрэлт амжилттай эсэхийг шалгаж, тохирох мессеж болон токен, хэрэглэгчийн ID-г response-д тохируулах
        if (user != null) {
            response.setSuccess(true);
            response.setMessage("Нэвтрэлт амжилттай.");
            response.setToken(user.getToken());

            if (user.getUserId() != null) {
                response.setUserId(user.getUserId());
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Нэвтрэх нэр эсвэл нууц үг буруу.");
        }

        return response;
    }

    //token-ний хүчинтэй эсэхийг шалгах method
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest request) {
        ValidateTokenResponse response = new ValidateTokenResponse();

        boolean valid = authService.validateToken(request.getToken());
        response.setValid(valid);

        if (valid) {
            response.setMessage("Token хүчинтэй.");

            AuthUser user = authService.getUserByToken(request.getToken());
            if (user != null && user.getUserId() != null) {
                response.setUserId(user.getUserId());
            }
        } else {
            response.setMessage("Token хүчингүй байна.");
        }

        return response;
    }
    
    //нэвтэрсэн хэрэглэгчийн профайл ID-г хэрэглэгчийн profile ID-тэй холбох method
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LinkUserProfileRequest")
    @ResponsePayload
    public LinkUserProfileResponse linkUserProfile(@RequestPayload LinkUserProfileRequest request) {
        LinkUserProfileResponse response = new LinkUserProfileResponse();

        boolean success = authService.attachUserProfileId(request.getUsername(), request.getUserId());

        response.setSuccess(success);
        if (success) {
            response.setMessage("Profile ID амжилттай холбогдлоо.");
        } else {
            response.setMessage("Хэрэглэгч олдсонгүй.");
        }

        return response;
    }
}