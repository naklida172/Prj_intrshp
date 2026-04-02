package com.adilkan.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.adilkan.demo.dtos.UserDTO;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.services.UserService;


@EnableWebMvc
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsersDTO() {
        return userService.getAllUsersDTO();
    }
    
    @GetMapping("/{id}")
    public UserDTO getUserByIdDTO(@PathVariable Long id) {
        return userService.getUserByIdDTO(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUserDTO(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUserDTO(id, userDTO);
    }
}
