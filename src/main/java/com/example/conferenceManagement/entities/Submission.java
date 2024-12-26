package com.example.conferenceManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String pdfUrl;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Conference conference;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;
}
