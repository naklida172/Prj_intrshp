package com.adilkan.demo.services.implementation;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.UserDTO;
import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.exceptions.InvalidCredentialsException;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.repositories.AuthTokenRepository;
import com.adilkan.demo.repositories.UserRepository;
import com.adilkan.demo.requests.RegisterRequest;
import com.adilkan.demo.services.AuthService;
import com.adilkan.demo.services.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private UserService userService;

    @Override
    public AuthToken createAuthToken(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null.");
        }

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(username);
        }

        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found with username or email: " + username);
        }

        User user = userOpt.get();
        if (!password.equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        AuthToken authToken = AuthToken.builder()
                .user(user)
                .token(UUID.randomUUID())
                .createdAt(new Date())
                .build();

        return authTokenRepository.save(authToken);
    }

    @Override
    public AuthToken registerUser(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new IllegalArgumentException("RegisterRequest cannot be null.");
        }

        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null
                || registerRequest.getEmail() == null) {
            throw new IllegalArgumentException("Username, password, and email are required.");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        UserDTO userDTO = UserDTO.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .role("USER")
                .build();

        User user = userService.createUser(userDTO);

        AuthToken authToken = AuthToken.builder()
                .user(user)
                .token(UUID.randomUUID())
                .createdAt(new Date())
                .build();

        return authTokenRepository.save(authToken);
    }

    @Override
    public void invalidateAuthToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null.");
        }

        try {
            UUID tokenUUID = UUID.fromString(token);
            Optional<AuthToken> authTokenOpt = authTokenRepository.findByToken(tokenUUID);

            if (authTokenOpt.isPresent()) {
                authTokenRepository.delete(authTokenOpt.get());
            } else {
                throw new ResourceNotFoundException("AuthToken not found with token: " + token);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token format.");
        }
    }

    @Override
    public boolean isValidAuthToken(String token) {
        if (token == null) {
            return false;
        }

        try {
            UUID tokenUUID = UUID.fromString(token);
            return authTokenRepository.findByToken(tokenUUID).isPresent();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

