package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.UserRepository;
import com.example.conferenceManagement.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        // map over the users and turn them into userDTO objects for security concerns
        return users.stream().map((user) -> mapToUserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToUserDTO(user);
    }

    @Override
    public UserDTO createUser(User newUser) {
        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());

        // Create User entity from UserDTO
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(encodedPassword);

        // Save user to the database
        User savedUser = userRepository.save(user);

        // Return the UserDTO without password
        return mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO signin(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials");
        }
        return mapToUserDTO(user);
    }


    // Convert User entity to UserDTO for returning to the client
    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
        return userDTO;
    }
}
