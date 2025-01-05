package com.example.conferenceManagement.unittestService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.services.impl.ConferenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ConferenceServiceImplTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    private ConferenceServiceImpl conferenceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conferenceService = new ConferenceServiceImpl(conferenceRepository);
    }

    @Test
    void testFindAllConferences() {
        // Arrange
        Conference conference1 = Conference.builder()
                .id(1L)
                .title("AI Revolution 2025")
                .startDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 3))
                .theme("Artificial Intelligence")
                .status(EConferenceStatus.OPEN)
                .build();

        Conference conference2 = Conference.builder()
                .id(2L)
                .title("Green Tech Summit")
                .startDate(LocalDate.of(2025, 5, 10))
                .endDate(LocalDate.of(2025, 5, 12))
                .theme("Sustainability")
                .status(EConferenceStatus.CLOSED)
                .build();

        when(conferenceRepository.findAll()).thenReturn(Arrays.asList(conference1, conference2));

        // Act
        List<Conference> conferences = conferenceService.findAllConferences();

        // Assert
        assertEquals(2, conferences.size());
        assertEquals("AI Revolution 2025", conferences.get(0).getTitle());
        assertEquals(EConferenceStatus.OPEN, conferences.get(0).getStatus());
        verify(conferenceRepository, times(1)).findAll();
    }

    @Test
    void testFindConferenceById_Exists() {
        // Arrange
        Long conferenceId = 1L;
        Conference conference = Conference.builder()
                .id(conferenceId)
                .title("AI Revolution 2025")
                .startDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 3))
                .theme("Artificial Intelligence")
                .status(EConferenceStatus.OPEN)
                .build();

        when(conferenceRepository.findById(conferenceId)).thenReturn(Optional.of(conference));

        // Act
        Conference foundConference = conferenceService.findConferenceById(conferenceId);

        // Assert
        assertNotNull(foundConference);
        assertEquals("AI Revolution 2025", foundConference.getTitle());
        verify(conferenceRepository, times(1)).findById(conferenceId);
    }

    @Test
    void testFindConferenceById_NotFound() {
        // Arrange
        Long conferenceId = 1L;
        when(conferenceRepository.findById(conferenceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            conferenceService.findConferenceById(conferenceId);
        });
    }

    @Test
    void testCreateConference() {
        // Arrange
        Conference newConference = Conference.builder()
                .title("AI Conference")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .theme("AI & Robotics")
                .status(EConferenceStatus.OPEN)
                .build();

        Conference savedConference = Conference.builder()
                .id(1L) // Mock ID after save
                .title("AI Conference")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .theme("AI & Robotics")
                .status(EConferenceStatus.OPEN)
                .build();

        when(conferenceRepository.save(any(Conference.class))).thenReturn(savedConference);

        // Act
        Conference createdConference = conferenceService.createConference(newConference);

        // Assert
        assertNotNull(createdConference);
        assertEquals(1L, createdConference.getId());
        assertEquals(EConferenceStatus.OPEN, createdConference.getStatus());
        verify(conferenceRepository, times(1)).save(any(Conference.class));
    }



}

