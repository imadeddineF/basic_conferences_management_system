package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.dto.EvaluationResponseDTO;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.dto.SubmitEvaluationDTO;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/{evaluationId}")
    public ResponseEntity<Evaluation> getEvaluationById(
            @PathVariable Long evaluationId
    ) {
        return ResponseEntity.ok(evaluationService.findEvaluationById(evaluationId));
    }

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(
            @RequestBody @Valid Evaluation evaluation
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(evaluationService.createEvaluation(evaluation));
    }

    @PostMapping("/{evaluationId}/submit")
    public ResponseEntity<Evaluation> submitEvaluation(
            @PathVariable Long evaluationId,
            @RequestBody @Valid SubmitEvaluationDTO request
    ) {
        // Validate status (only allow ACCEPTED/REJECTED from evaluators)
        if (request.getStatus() != ESubmissionStatus.ACCEPTED &&
                request.getStatus() != ESubmissionStatus.REJECTED) {
            throw new IllegalArgumentException("Status must be ACCEPTED or REJECTED");
        }

        Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);

        evaluation.setScore(request.getScore());
        evaluation.setComment(request.getComment());
        evaluation.setStatus(request.getStatus()); // Set evaluator's decision

        return ResponseEntity.ok(evaluationService.createEvaluation(evaluation));
    }

    @GetMapping
    public ResponseEntity<List<EvaluationResponseDTO>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.findAllEvaluations());
    }

    // for editors to retrieve accepted evaluations
    @GetMapping("/accepted")
    public ResponseEntity<List<EvaluationResponseDTO>> getAcceptedEvaluations() {
        return ResponseEntity.ok(
                evaluationService.findEvaluationsByStatus(ESubmissionStatus.ACCEPTED)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}