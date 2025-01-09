package com.example.conferenceManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    // Add this constructor
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Default constructor (required for frameworks like Jackson)
    public LoginDTO() {
    }
}
