package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ConferenceRepositoryTest {

    @Autowired
    private ConferenceRepository conferenceRepository;

    private Conference testConference;

    @BeforeEach
    void setUp() {
        // Initialize a test conference
        testConference = new Conference();
        testConference.setTitle("Spring Boot Conference");
        testConference.setStartDate(LocalDate.of(2025, 1, 20));
        testConference.setEndDate(LocalDate.of(2025, 1, 22));
        testConference.setTheme("Technology and Innovation");
        testConference.setStatus(EConferenceStatus.EVALUATION);

        // Save the test conference to the repository
        conferenceRepository.save(testConference);
    }

    @Test
    void testFindByTitle() {
        Optional<Conference> conference = conferenceRepository.findByTitle("Spring Boot Conference");
        assertThat(conference).isPresent();
        assertThat(conference.get().getTitle()).isEqualTo(testConference.getTitle());
    }

    @Test
    void testFindByStartDate() {
        Optional<Conference> conference = conferenceRepository.findByStartDate(LocalDate.of(2025, 1, 20));
        assertThat(conference).isPresent();
        assertThat(conference.get().getStartDate()).isEqualTo(testConference.getStartDate());
    }

    @Test
    void testFindByEndDate() {
        Optional<Conference> conference = conferenceRepository.findByEndDate(LocalDate.of(2025, 1, 22));
        assertThat(conference).isPresent();
        assertThat(conference.get().getEndDate()).isEqualTo(testConference.getEndDate());
    }

    @Test
    void testFindByTheme() {
        Optional<Conference> conference = conferenceRepository.findByTheme("Technology and Innovation");
        assertThat(conference).isPresent();
        assertThat(conference.get().getTheme()).isEqualTo(testConference.getTheme());
    }

    @Test
    void testFindByStatus() {
        Optional<Conference> conference = conferenceRepository.findByStatus(EConferenceStatus.EVALUATION);
        assertThat(conference).isPresent();
        assertThat(conference.get().getStatus()).isEqualTo(testConference.getStatus());
    }
}
