package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    Optional<Conference> findByTitle(String title);
    Optional<Conference> findByStartDate(LocalDate startDate);
    Optional<Conference> findByEndDate(LocalDate endDate);
    Optional<Conference> findByTheme(String theme);
    Optional<Conference> findByStatus(EConferenceStatus status);
}

