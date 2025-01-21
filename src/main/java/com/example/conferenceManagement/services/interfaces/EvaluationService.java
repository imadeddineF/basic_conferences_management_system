package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.EvaluationResponseDTO;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;

import java.util.List;

public interface EvaluationService {
    Evaluation findEvaluationById(Long evaluationId);
    Evaluation createEvaluation(Evaluation evaluation);
    List<EvaluationResponseDTO> findAllEvaluations();
    List<EvaluationResponseDTO> findEvaluationsByStatus(ESubmissionStatus status);
}
