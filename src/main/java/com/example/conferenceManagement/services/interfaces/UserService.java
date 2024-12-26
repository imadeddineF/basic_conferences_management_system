package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;

import java.util.List;


public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Long userId);
    UserDTO createUser(User newUser);
}
