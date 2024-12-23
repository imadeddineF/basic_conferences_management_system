package com.example.conferenceManagement.entities;


import jakarta.persistence.*;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int score; // 1 to 10

    @Column(nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Submission submission;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User reviewer;
}
