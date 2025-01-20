package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.EvaluationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
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

    @PostMapping("/addEvaluation")
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
            @RequestParam @Min(1) @Max(10) int score,
            @RequestParam String comment
    ) {
        Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);

        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be blank");
        }

        evaluation.setScore(score);
        evaluation.setComment(comment);
        evaluation.setStatus(ESubmissionStatus.PENDING);

        return ResponseEntity.ok(evaluationService.createEvaluation(evaluation));
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