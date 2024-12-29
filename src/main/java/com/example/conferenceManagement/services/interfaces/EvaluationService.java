package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.entities.Evaluation;

public interface EvaluationService {
    Evaluation findEvaluationById(Long evaluationId);
    Evaluation createEvaluation(Evaluation evaluation);
}
