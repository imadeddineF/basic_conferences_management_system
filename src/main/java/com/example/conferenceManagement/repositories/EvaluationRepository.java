package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
  
}
