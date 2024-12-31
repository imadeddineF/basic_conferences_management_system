package com.example.conferenceManagement.entities;


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

    @NotNull(message = "Score cannot be null")
    /*@Size(min = 1, max = 10, message = "Score must be between 1 and 10") */
    private int score; // 1 to 10

    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    @ElementCollection
    @CollectionTable(
            name = "evaluation_comments",
            joinColumns = @JoinColumn(name = "evaluation_id")
    )
    @Column(name = "comment", columnDefinition = "TEXT")
    private List<String> comments;

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
}
