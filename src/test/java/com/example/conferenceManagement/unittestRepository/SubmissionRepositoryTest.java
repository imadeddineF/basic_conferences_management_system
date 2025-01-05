package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.repositories.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    private Conference conference;

    @BeforeEach
    void setUp() {
        // Create and save a Conference
        conference = new Conference();
        conference.setTitle("AIw Conference");
        conference.setTheme("Artificial Intelligence");
        conference.setDescription("Conference about AI advancements.");
        conferenceRepository.save(conference);

        // Create and save a Submission
        Submission submission = new Submission();
        submission.setTitle("AI in Healthcare");
        submission.setSummary("Using AI to improve healthcare.");
        submission.setPdfUrl("http://example.com/ai-healthcare.pdf");
        submission.setStatus(ESubmissionStatus.PENDING);
        submission.setConference(conference);
        submissionRepository.save(submission);
    }

    @Test
    void testFindByTitle() {
        // Act
        Optional<Submission> result = submissionRepository.findByTitle("AI in Healthcare");

        // Assert
        assertTrue(result.isPresent(), "Submission should be found by title.");
        assertEquals("AI in Healthcare", result.get().getTitle(), "Title should match.");
    }

    @Test
    void testFindByStatus() {
        // Act
        Optional<Submission> result = submissionRepository.findByStatus(ESubmissionStatus.PENDING);

        // Assert
        assertTrue(result.isPresent(), "Submission should be found by status.");
        assertEquals(ESubmissionStatus.PENDING, result.get().getStatus(), "Status should match.");
    }

    @Test
    void testFindByConference() {
        // Act
        Optional<Submission> result = submissionRepository.findByConference(conference);

        // Assert
        assertTrue(result.isPresent(), "Submission should be found by conference.");
        assertEquals("AI Conference", result.get().getConference().getTitle(), "Conference title should match.");
    }
}
