package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Remove @NotNull to allow null for pending evaluations
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 10, message = "Score must be at most 10")
    private Integer score; // Keep as Integer (nullable)

    // Single comment field (not a list)
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment; // No @NotBlank

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private ESubmissionStatus status;

    @NotNull(message = "Submission cannot be null")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Submission submission;

    @NotNull(message = "Reviewer cannot be null")
    @ManyToOne
    @JoinColumn(nullable = false)
    private User reviewer;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    // Remove this conflicting field (duplicate of 'comment')
    // @ElementCollection
    // @CollectionTable(...)
    // private List<String> comments;
}