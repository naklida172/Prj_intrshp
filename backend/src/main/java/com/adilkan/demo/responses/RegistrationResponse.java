package com.adilkan.demo.responses;

import com.adilkan.demo.dtos.AuthTokenDTO;
import com.adilkan.demo.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private UserDTO user;
    private AuthTokenDTO authToken;
}
