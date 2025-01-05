package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EvaluationRepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;

    // Before each test, clear the repository (optional)
    @BeforeEach
    void setUp() {
        evaluationRepository.deleteAll();
    }

    @Test
    void testFindByStatus() {
        // Arrange: Save an Evaluation object with a specific status
        Evaluation evaluation = new Evaluation();
        evaluation.setStatus(ESubmissionStatus.PENDING);
        evaluationRepository.save(evaluation);

        // Act: Retrieve the evaluation by its status
        Optional<Evaluation> retrievedEvaluation = evaluationRepository.findByStatus(ESubmissionStatus.PENDING);

        // Assert: Verify the evaluation was found and matches the expected status
        assertTrue(retrievedEvaluation.isPresent(), "The evaluation should be found");
        assertEquals(ESubmissionStatus.PENDING, retrievedEvaluation.get().getStatus(), "The status should match");
    }

    @Test
    void testFindByStatus_NoResults() {
        // Act: Try to find an evaluation with a non-existing status
        Optional<Evaluation> retrievedEvaluation = evaluationRepository.findByStatus(ESubmissionStatus.ACCEPTED);

        // Assert: Verify no evaluation is found
        assertFalse(retrievedEvaluation.isPresent(), "No evaluation should be found with the status APPROVED");
    }
}
