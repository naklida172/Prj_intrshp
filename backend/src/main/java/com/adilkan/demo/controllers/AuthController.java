package com.adilkan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.adilkan.demo.dtos.AuthTokenDTO;
import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.exceptions.InvalidCredentialsException;
import com.adilkan.demo.mappers.AuthTokenMapper;
import com.adilkan.demo.requests.LoginRequest;
import com.adilkan.demo.requests.RegisterRequest;
import com.adilkan.demo.services.AuthService;

@EnableWebMvc
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //TODO: Implement token refresh

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> createAuth(@RequestBody LoginRequest loginRequest) {
        AuthToken authToken = authService.createAuthToken(loginRequest.getUsername(), loginRequest.getPassword());
        if (authToken != null) {
            return ResponseEntity.ok(AuthTokenMapper.toDTO(authToken)); // Returns 200 OK
        }
        throw new InvalidCredentialsException();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String token) {
        authService.invalidateAuthToken(token);
        return ResponseEntity.ok().build(); // Returns 200 OK
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenDTO> refresh(@RequestBody String token) {
        // Implementation for token refresh
        return ResponseEntity.ok().build(); // Returns 200 OK
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthTokenDTO> register(@RequestBody RegisterRequest registerRequest) {
        AuthToken authToken = authService.registerUser(registerRequest);
        return ResponseEntity.ok(AuthTokenMapper.toDTO(authToken)); // Returns 200 OK
    }
}
