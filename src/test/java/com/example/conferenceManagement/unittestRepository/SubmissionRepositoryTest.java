package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.repositories.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    private Conference testConference;
    private Submission testSubmission;

    @BeforeEach
    void setUp() {
        // Clear repositories
        submissionRepository.deleteAll();
        conferenceRepository.deleteAll();

        // Create and save a test conference
        testConference = new Conference();
        testConference.setTitle("Tech Innovations Conference");
        testConference.setStartDate(LocalDate.of(2025, 2, 10));
        testConference.setEndDate(LocalDate.of(2025, 2, 12));
        testConference.setTheme("AI and Machine Learning");
        testConference.setStatus(EConferenceStatus.EVALUATION);
        conferenceRepository.save(testConference);

        // Create and save a test submission
        testSubmission = new Submission();
        testSubmission.setTitle("Revolutionizing AI");
        testSubmission.setSummary("Exploring new frontiers in artificial intelligence.");
        testSubmission.setPdfUrl("http://example.com/revolutionizing-ai.pdf");
        testSubmission.setStatus(ESubmissionStatus.PENDING);
        testSubmission.setConference(testConference); // Link to the test conference
        submissionRepository.save(testSubmission);
    }

    @Test
    void testFindByTitle() {
        // Act
        Optional<Submission> foundSubmission = submissionRepository.findByTitle("Revolutionizing AI");

        // Assert
        assertThat(foundSubmission).isPresent();
        assertThat(foundSubmission.get().getTitle()).isEqualTo(testSubmission.getTitle());
    }

    @Test
    void testFindByStatus() {
        // Act
        Optional<Submission> foundSubmission = submissionRepository.findByStatus(ESubmissionStatus.PENDING);

        // Assert
        assertThat(foundSubmission).isPresent();
        assertThat(foundSubmission.get().getStatus()).isEqualTo(testSubmission.getStatus());
    }

    @Test
    void testFindByConference() {
        // Act
        Optional<Submission> foundSubmission = submissionRepository.findByConference(testConference);

        // Assert
        assertThat(foundSubmission).isPresent();
        assertThat(foundSubmission.get().getConference().getId()).isEqualTo(testConference.getId());
        assertThat(foundSubmission.get().getConference().getTitle()).isEqualTo(testConference.getTitle());
    }
}
