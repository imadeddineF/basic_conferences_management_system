package com.example.conferenceManagement.entities;


import com.example.conferenceManagement.enums.EEvaluationStatus;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private int score; // 1 to 10

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ElementCollection
    @CollectionTable(
            name = "evaluation_comments",
            joinColumns = @JoinColumn(name = "evaluation_id")
    )
    @Column(name = "comment", columnDefinition = "TEXT")
    private List<String> comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EEvaluationStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Submission submission;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User reviewer;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
