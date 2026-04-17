package com.adilkan.demo.config;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.repositories.AuthTokenRepository;
import com.adilkan.demo.repositories.UserRepository;

//TODO: Fix the hardcoded user ID in the OAuth2 success handler and implement proper user lookup/creation based on OAuth2 info

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/login/oauth2/**").permitAll() // Allow OAuth2 tokens check
                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> {
                    if (authentication instanceof OAuth2AuthenticationToken) {
                        AuthToken authToken = AuthToken.builder()
                                .user(userRepository.findById(6L).orElseThrow(() -> new RuntimeException("User not found!"))) //change later
                                .token(UUID.randomUUID())
                                .createdAt(new Date())
                                .expiresAt(new Date(System.currentTimeMillis() + 60000))
                                .build();

                        authTokenRepository.save(authToken);

                        response.setHeader("Authorization", "Bearer " + authToken.getToken());
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{ \"token\": \"" + authToken.getToken() + "\" }");
                    }
                })
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
