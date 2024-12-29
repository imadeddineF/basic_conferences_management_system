package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Summary cannot be blank")
    private String summary;

    @NotBlank(message = "PDF URL cannot be blank")
    private String pdfUrl;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private ESubmissionStatus status;

    @NotNull(message = "Conference cannot be null")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Conference conference;

    @ManyToMany
    @JoinTable(
            name = "submission_authors",
            joinColumns = @JoinColumn(name = "submission_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> authors;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}