package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.EUserRole;
import jakarta.persistence.*;

@Entity
@IdClass(UserRole.class)
public class UserRole {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUserRole role; // EDITOR, AUTHOR, REVIEWER
}

