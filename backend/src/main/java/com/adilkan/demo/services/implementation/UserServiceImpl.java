package com.adilkan.demo.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.UserDTO;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.mappers.UserMapper;
import com.adilkan.demo.repositories.UserRepository;
import com.adilkan.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null.");
        }

        User user = UserMapper.toEntity(userDTO);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        return users;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new IllegalArgumentException("ID and UserDTO cannot be null.");
        }

        User existingUser = getUserById(id);
        existingUser.setName(userDTO.getName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setRole(userDTO.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsersDTO() {
        List<User> users = userRepository.findAll();
        
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }

        return users.stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByIdDTO(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
    
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public UserDTO updateUserDTO(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new IllegalArgumentException("ID and UserDTO cannot be null.");
        }
    
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    
        existingUser.setName(userDTO.getName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setRole(userDTO.getRole());
    
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDTO(updatedUser);
    }
    
}
