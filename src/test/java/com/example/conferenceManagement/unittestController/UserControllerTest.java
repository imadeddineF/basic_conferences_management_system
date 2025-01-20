package com.example.conferenceManagement.unittestController;

import com.example.conferenceManagement.controllers.UserController;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService; // Mock the UserService

    @InjectMocks
    private UserController userController; // Inject the mock into UserController

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        UserDTO user = UserDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));

        // Act and Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Ensure the list contains one user
                .andExpect(jsonPath("$[0].firstName", is("John"))) // Check the firstName
                .andExpect(jsonPath("$[0].lastName", is("Doe"))) // Check the lastName
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com"))); // Check the email
    }

    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        UserDTO user = UserDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        when(userService.findUserById(1L)).thenReturn(user);

        // Act and Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1))) // Check the ID
                .andExpect(jsonPath("$.firstName", is("John"))) // Check the firstName
                .andExpect(jsonPath("$.lastName", is("Doe"))) // Check the lastName
                .andExpect(jsonPath("$.email", is("john.doe@example.com"))); // Check the email
    }
}
