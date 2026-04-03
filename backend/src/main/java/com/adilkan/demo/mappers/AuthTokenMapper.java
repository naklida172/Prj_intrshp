package com.adilkan.demo.mappers;

import com.adilkan.demo.dtos.AuthTokenDTO;
import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.entities.User;

public class AuthTokenMapper {
    public static AuthTokenDTO toDTO(AuthToken authToken) {
        if (authToken == null) return null;
        return AuthTokenDTO.builder()
                .id(authToken.getId())
                .token(authToken.getToken())
                .createdAt(authToken.getCreatedAt())
                .userId(authToken.getUser() != null ? authToken.getUser().getId() : null)
                .build();
    }

    public static AuthToken toEntity(AuthTokenDTO authTokenDTO) {
        if (authTokenDTO == null) return null;

        User user = authTokenDTO.getUserId() != null ? User.builder().id(authTokenDTO.getUserId()).build() : null;

        return AuthToken.builder()
                .id(authTokenDTO.getId())
                .token(authTokenDTO.getToken())
                .createdAt(authTokenDTO.getCreatedAt())
                .user(user)
                .build();
    }
}
