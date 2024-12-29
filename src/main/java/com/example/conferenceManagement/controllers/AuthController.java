package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.dto.LoginDTO;
import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestBody @Valid LoginDTO loginDTO) {
        UserDTO user = userService.signin(loginDTO);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody @Valid User newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
