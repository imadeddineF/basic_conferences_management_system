package com.example.conferenceManagement.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
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
