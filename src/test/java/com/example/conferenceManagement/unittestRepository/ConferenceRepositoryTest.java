package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ConferenceRepositoryTest {

    @BeforeEach
    void setUp() {
        conferenceRepository.deleteAll(); // Optional: clean up before each test
    }

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    void testFindByTitle() {
        // Arrange
        Conference conference = Conference.builder()
                .title("AI Revolution 2025")
                .startDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 3))
                .theme("Artificial Intelligence")
                .status(EConferenceStatus.OPEN)
                .build();

        conferenceRepository.save(conference);

        // Act
        Optional<Conference> foundConference = conferenceRepository.findByTitle("AI Revolution 2025");

        // Assert
        assertTrue(foundConference.isPresent());
        assertEquals("AI Revolution 2025", foundConference.get().getTitle());
    }

    @Test
    void testFindByStartDate() {
        // Arrange
        Conference conference = Conference.builder()
                .title("Tech Summit")
                .startDate(LocalDate.of(2025, 5, 10))
                .endDate(LocalDate.of(2025, 5, 12))
                .theme("Innovation")
                .status(EConferenceStatus.CLOSED)
                .build();

        conferenceRepository.save(conference);

        // Act
        Optional<Conference> foundConference = conferenceRepository.findByStartDate(LocalDate.of(2025, 5, 10));

        // Assert
        assertTrue(foundConference.isPresent());
        assertEquals(LocalDate.of(2025, 5, 10), foundConference.get().getStartDate());
    }

    @Test
    void testFindByTheme() {
        // Arrange
        Conference conference = Conference.builder()
                .title("Sustainability Summit")
                .startDate(LocalDate.of(2025, 7, 20))
                .endDate(LocalDate.of(2025, 7, 22))
                .theme("Green Tech")
                .status(EConferenceStatus.OPEN)
                .build();

        conferenceRepository.save(conference);

        // Act
        Optional<Conference> foundConference = conferenceRepository.findByTheme("Green Tech");

        // Assert
        assertTrue(foundConference.isPresent());
        assertEquals("Green Tech", foundConference.get().getTheme());
    }
/*
    @Test
    void testFindByStatus() {
        // Arrange
        Conference conference = Conference.builder()
                .title("Robotics Expo")
                .startDate(LocalDate.of(2025, 8, 15))
                .endDate(LocalDate.of(2025, 8, 17))
                .theme("Automation")
                .status(EConferenceStatus.OPEN)
                .build();

        conferenceRepository.save(conference);

        // Act
        Optional<Conference> foundConference = conferenceRepository.findByStatus(EConferenceStatus.OPEN);

        // Assert
        assertTrue(foundConference.isPresent());
        assertEquals(EConferenceStatus.CLOSED, foundConference.get().getStatus());
    }
    
 */
}
