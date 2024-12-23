package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String thematic;

    @Enumerated(EnumType.STRING)
    private ESubmissionStatus status; // OPEN, CLOSED, REVIEWING

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;
}
