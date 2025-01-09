package com.example.conferenceManagement.unittestService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.services.impl.DecisionServiceImpl;

import java.util.Optional;

class DecisionServiceImplTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    private DecisionServiceImpl decisionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        decisionService = new DecisionServiceImpl(conferenceRepository);
    }

    @Test
    void testFindConferenceDecisionById_Exists() {
        // Arrange
        Long conferenceId = 1L;
        Conference conference = new Conference();
        conference.setId(conferenceId);
        conference.setStatus(EConferenceStatus.OPEN);  // Status should be OPEN for this test
        when(conferenceRepository.findById(conferenceId)).thenReturn(Optional.of(conference));

        // Act
        EConferenceStatus status = decisionService.findConferenceDecisionById(conferenceId);

        // Assert
        assertNotNull(status);
        assertEquals(EConferenceStatus.OPEN, status);
        verify(conferenceRepository, times(1)).findById(conferenceId);
    }

    @Test
    void testFindConferenceDecisionById_NotFound() {
        // Arrange
        Long conferenceId = 1L;
        when(conferenceRepository.findById(conferenceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            decisionService.findConferenceDecisionById(conferenceId);
        });
        verify(conferenceRepository, times(1)).findById(conferenceId);
    }

}
