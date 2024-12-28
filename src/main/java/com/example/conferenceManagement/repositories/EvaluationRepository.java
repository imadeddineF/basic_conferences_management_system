package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findByStatus(ESubmissionStatus status);
}
