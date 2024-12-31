package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import com.example.conferenceManagement.services.interfaces.EvaluationService;
import org.springframework.stereotype.Service;
@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public Evaluation findEvaluationById(Long evaluationId) {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + evaluationId));
    }

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        validateScore(evaluation.getScore());
        return evaluationRepository.save(evaluation);
    }

    private void validateScore(int score) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Score must be between 1 and 10.");
        }
    }
}
