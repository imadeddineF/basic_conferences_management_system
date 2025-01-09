package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    // @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotBlank(message = "Theme cannot be blank")
    private String theme;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private EConferenceStatus status; // OPEN, CLOSED

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


}
