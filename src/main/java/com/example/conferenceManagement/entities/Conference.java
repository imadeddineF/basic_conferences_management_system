package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conferences")
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
    private String theme;

    @Enumerated(EnumType.STRING)
    private ESubmissionStatus status; // OPEN, CLOSED, REVIEWING

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
