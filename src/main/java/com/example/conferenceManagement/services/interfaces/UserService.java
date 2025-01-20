package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<UserDTO> findAllUsers();
    // UserDTO findUserById(Long userId);
    UserDTO createUser(User newUser);
    UserDTO signin(LoginDTO loginDTO);
    // For internal use (e.g., role assignment)
    Optional<User> findUserEntityById(Long userId);
    // For API responses
    UserDTO findUserById(Long userId);
}
