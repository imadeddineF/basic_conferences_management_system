package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.entities.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<UserRole> roles;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
