package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import com.example.conferenceManagement.repositories.SubmissionRepository;
import com.example.conferenceManagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EvaluationRepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConferenceRepository conferenceRepository;

    private Conference conference;

    @BeforeEach
    void setUp() {
        evaluationRepository.deleteAll();
        submissionRepository.deleteAll();
        userRepository.deleteAll();
        conferenceRepository.deleteAll();

        // Create and save a Conference
        conference = new Conference();
        conference.setTitle("Spring Boot Conference");
        conference.setStartDate(LocalDate.of(2025, 1, 20));
        conference.setEndDate(LocalDate.of(2025, 1, 22));
        conference.setTheme("Technology and Innovation");
        conference.setStatus(EConferenceStatus.EVALUATION);
        conferenceRepository.save(conference);
    }

    @Test
    void testFindByStatus() {
        // Arrange
        Submission submission = new Submission();
        submission.setTitle("Test Submission");
        submission.setSummary("This is a test summary");
        submission.setPdfUrl("http://example.com/test.pdf");
        submission.setStatus(ESubmissionStatus.PENDING);
        submission.setConference(conference); // Associate with the saved Conference
        submissionRepository.save(submission);

        User reviewer = new User();
        reviewer.setFirstName("John");
        reviewer.setLastName("Doe");
        reviewer.setEmail("john.doe@example.com");
        reviewer.setPassword("password123");
        userRepository.save(reviewer);

        Evaluation evaluation = new Evaluation();
        evaluation.setStatus(ESubmissionStatus.PENDING);
        evaluation.setComment("This is a valid comment");
        evaluation.setScore(5);
        evaluation.setSubmission(submission);
        evaluation.setReviewer(reviewer);
        evaluationRepository.save(evaluation);

        // Act
        Optional<Evaluation> retrievedEvaluation = evaluationRepository.findByStatus(ESubmissionStatus.PENDING);

        // Assert
        assertThat(retrievedEvaluation).isPresent();
        assertThat(retrievedEvaluation.get().getStatus()).isEqualTo(ESubmissionStatus.PENDING);
    }
}
