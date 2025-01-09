package com.example.conferenceManagement.unittestService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import com.example.conferenceManagement.services.impl.EvaluationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

class EvaluationServiceImplTest {

    @Mock
    private EvaluationRepository evaluationRepository;

    @InjectMocks
    private EvaluationServiceImpl evaluationService;

    private Evaluation evaluation;
    private Long evaluationId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a sample Evaluation entity to be used in tests
        evaluation = Evaluation.builder()
                .id(evaluationId)
                .score(8)
                .comment("Great submission!")
                .status(ESubmissionStatus.ACCEPTED)
                .build();
    }

    @Test
    void testFindEvaluationById_Exists() {
        // Arrange
        when(evaluationRepository.findById(evaluationId)).thenReturn(Optional.of(evaluation));

        // Act
        Evaluation foundEvaluation = evaluationService.findEvaluationById(evaluationId);

        // Assert
        assertNotNull(foundEvaluation);
        assertEquals(evaluationId, foundEvaluation.getId());
        assertEquals(8, foundEvaluation.getScore());
        verify(evaluationRepository, times(1)).findById(evaluationId);
    }

    @Test
    void testFindEvaluationById_NotFound() {
        // Arrange
        when(evaluationRepository.findById(evaluationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            evaluationService.findEvaluationById(evaluationId);
        });
    }

    @Test
    void testCreateEvaluation() {
        // Arrange
        Evaluation newEvaluation = Evaluation.builder()
                .score(9)
                .comment("Excellent work!")
                .status(ESubmissionStatus.PENDING)
                .build();

        // Mock the save operation
        when(evaluationRepository.save(any(Evaluation.class))).thenReturn(newEvaluation);

        // Act
        Evaluation createdEvaluation = evaluationService.createEvaluation(newEvaluation);

        // Assert
        assertNotNull(createdEvaluation);
        assertEquals(9, createdEvaluation.getScore());
        assertEquals("Excellent work!", createdEvaluation.getComment());
        verify(evaluationRepository, times(1)).save(any(Evaluation.class));
    }


}
