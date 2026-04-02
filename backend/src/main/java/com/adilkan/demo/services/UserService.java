package com.adilkan.demo.services;

import java.util.List;

import com.adilkan.demo.dtos.UserDTO;
import com.adilkan.demo.entities.User;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    List<UserDTO> getAllUsersDTO();
    UserDTO getUserByIdDTO(Long id);
    UserDTO updateUserDTO(Long id, UserDTO userDTO);
}

