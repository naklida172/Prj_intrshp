package com.adilkan.demo.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenAuthentication extends AbstractAuthenticationToken {
    private final String token;
    private final UserDetails principal;

    public TokenAuthentication(String token) {
        super(java.util.Collections.emptyList()); // no authorities
        this.token = token;
        this.principal = null;
        setAuthenticated(false);
    }

    public TokenAuthentication(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}