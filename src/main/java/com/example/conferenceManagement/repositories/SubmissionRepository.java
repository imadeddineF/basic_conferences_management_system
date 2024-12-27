package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByTitle(String title);
    Optional<Submission> findByStatus(ESubmissionStatus status);
    Optional<Submission> findByConference(Conference conference);
}
