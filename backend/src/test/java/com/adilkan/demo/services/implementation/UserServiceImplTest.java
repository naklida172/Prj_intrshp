package com.adilkan.demo.services.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adilkan.demo.dtos.UserDTO;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("Test User")
                .username("testuser")
                .email("test@example.com")
                .build();

        testUserDTO = UserDTO.builder()
                .name("Test User")
                .username("testuser")
                .email("test@example.com")
                .build();
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User createdUser = userService.createUser(testUserDTO);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should get user by id successfully")
    void shouldGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        User foundUser = userService.getUserById(1L);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user profile not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id: 99");
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}
