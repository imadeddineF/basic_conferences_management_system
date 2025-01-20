package com.example.conferenceManagement.unittestController;

import com.example.conferenceManagement.controllers.AuthController;
import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSignin_Success() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password123");
        UserDTO userDTO = new UserDTO(
                1L,
                "Test",
                "User",
                "test@example.com",
                Collections.emptyList(), // Assuming no roles for simplicity
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(userService.signin(loginDTO)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> response = authController.signin(loginDTO);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("test@example.com");

        verify(userService, times(1)).signin(loginDTO);
    }


    @Test
    void testSignup_Success() {
        // Arrange
        User newUser = new User();
        newUser.setId(1L);
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmail("test@example.com");
        newUser.setPassword("password123");
        newUser.setRoles(Collections.emptyList()); // Assuming no roles for simplicity

        UserDTO userDTO = new UserDTO(
                1L,
                "Test",
                "User",
                "test@example.com",
                Collections.emptyList(), // Assuming no roles for simplicity
                null, // createdAt is not required
                null  // updatedAt is not required
        );

        when(userService.createUser(newUser)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> response = authController.signup(newUser);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Test");
        assertThat(response.getBody().getLastName()).isEqualTo("User");
        assertThat(response.getBody().getEmail()).isEqualTo("test@example.com");

        verify(userService, times(1)).createUser(newUser);
    }

}
