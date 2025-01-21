package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.dto.EvaluationResponseDTO;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import com.example.conferenceManagement.services.interfaces.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public List<EvaluationResponseDTO> findAllEvaluations() {
        return evaluationRepository.findAll()
                .stream()
                .map(this::mapToEvaluationResponseDTO)
                .collect(Collectors.toList());
    }

    private EvaluationResponseDTO mapToEvaluationResponseDTO(Evaluation evaluation) {
        return EvaluationResponseDTO.builder()
                .id(evaluation.getId())
                .score(evaluation.getScore())
                .comment(evaluation.getComment())
                .status(evaluation.getStatus())
                .submissionId(evaluation.getSubmission().getId())
                .reviewerId(evaluation.getReviewer().getId())
                .createAt(evaluation.getCreateAt())
                .updateAt(evaluation.getUpdateAt())
                .build();
    }

    @Override
    public Evaluation findEvaluationById(Long evaluationId) {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + evaluationId));
    }

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> findEvaluationsByStatus(ESubmissionStatus status) {
        return evaluationRepository.findByStatus(status);
    }
}