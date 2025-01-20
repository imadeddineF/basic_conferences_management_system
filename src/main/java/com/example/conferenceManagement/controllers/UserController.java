package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Base path for the API
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }
}
