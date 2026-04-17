package com.adilkan.demo.config;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.repositories.AuthTokenRepository;
import com.adilkan.demo.repositories.UserRepository;

@Service
public class TokenService {

    @Autowired
    private AuthTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<UserDetails> validateToken(String tokenStr) {
        try {
            UUID token = UUID.fromString(tokenStr);
            Optional<AuthToken> authToken = tokenRepository.findByToken(token);

            if (authToken.isPresent()) {
                User user = authToken.get().getUser();
                return Optional.of(
                    new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        "",
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    )
                );
            }

        } catch (IllegalArgumentException e) {
            return Optional.empty(); // invalid UUID
        }

        return Optional.empty();
    }
}