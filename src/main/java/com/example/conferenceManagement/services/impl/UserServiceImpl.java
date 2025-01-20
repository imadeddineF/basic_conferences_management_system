package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.dto.UserRoleInfoDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.UserRepository;
import com.example.conferenceManagement.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Optional<User> findUserEntityById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToUserDTO(user);
    }

    @Override
    public UserDTO createUser(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        User user = User.builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .password(encodedPassword)
                .roles(new ArrayList<>()) // Explicitly initialize roles
                .build();
        User savedUser = userRepository.save(user);
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
        List<UserRoleInfoDTO> roles = (user.getRoles() != null) ?
                user.getRoles().stream()
                        .map(role -> UserRoleInfoDTO.builder()
                                .conferenceId(role.getConference().getId())
                                .role(role.getId().getRole())
                                .build())
                        .collect(Collectors.toList())
                : new ArrayList<>(); // Fallback to empty list

        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
    }
}
