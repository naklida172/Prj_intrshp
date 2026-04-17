package com.adilkan.demo.config;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.repositories.AuthTokenRepository;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        Optional<AuthToken> authTokenOptional = authTokenRepository.findByToken(UUID.fromString(token));
        if (authTokenOptional.isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }

        AuthToken authToken = authTokenOptional.get();

        // Added expiration check
        if (authToken.getExpiresAt().before(new Date())) {
            throw new BadCredentialsException("Token expired");
        }

        Optional<UserDetails> userDetails = tokenService.validateToken(token);
        if (userDetails.isPresent()) {
            return new TokenAuthentication(userDetails.get(), token, userDetails.get().getAuthorities());
        }

        throw new BadCredentialsException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }
}
