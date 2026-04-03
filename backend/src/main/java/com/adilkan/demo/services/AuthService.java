package com.adilkan.demo.services;

import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.requests.RegisterRequest;

public interface AuthService {
    AuthToken createAuthToken(String username, String password);
    AuthToken registerUser(RegisterRequest registerRequest);
    void invalidateAuthToken(String token);
    boolean isValidAuthToken(String token);
}
