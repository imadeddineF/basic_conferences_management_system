package com.example.conferenceManagement.unittestService;


import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.UserRepository;
import com.example.conferenceManagement.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsers_Success() {
        // Arrange
        User user1 = User.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@example.com").build();
        User user2 = User.builder().id(2L).firstName("Jane").lastName("Smith").email("jane.smith@example.com").build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        var userDTOs = userService.findAllUsers();

        // Assert
        assertEquals(2, userDTOs.size());
        assertEquals("John", userDTOs.get(0).getFirstName());
        assertEquals("Jane", userDTOs.get(1).getFirstName());
    }

    @Test
    void findUserById_Success() {
        // Arrange
        Long userId = 1L;
        User user = User.builder().id(userId).firstName("John").lastName("Doe").email("john.doe@example.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDTO userDTO = userService.findUserById(userId);

        // Assert
        assertNotNull(userDTO);
        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());
    }

    @Test
    void findUserById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    void createUser_Success() {
        // Arrange
        User newUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        when(passwordEncoder.encode("password123")).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO userDTO = userService.createUser(newUser);

        // Assert
        assertNotNull(userDTO);
        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("encryptedPassword", captor.getValue().getPassword());
    }

    @Test
    void signin_Success() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "password123");

        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encryptedPassword")
                .build();

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encryptedPassword")).thenReturn(true);

        // Act
        UserDTO userDTO = userService.signin(loginDTO);

        // Assert
        assertNotNull(userDTO);
        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());
    }

    @Test
    void signin_InvalidCredentials() {

        // Arrange
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "wrongPassword");

        User user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("encryptedPassword")
                .build();

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.signin(loginDTO));
    }

    @Test
    void signin_UserNotFound() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("nonexistent@example.com", "password123");

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.signin(loginDTO));
    }
}
