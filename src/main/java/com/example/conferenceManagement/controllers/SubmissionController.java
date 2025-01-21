package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.dto.SubmissionRequestDTO;
import com.example.conferenceManagement.dto.SubmissionResponseDTO;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponseDTO>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.findAllSubmissions());
    }

    @PostMapping("/addSubmission")
    public ResponseEntity<SubmissionResponseDTO> createSubmission(
            @RequestBody @Valid SubmissionRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(submissionService.createSubmission(request));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionById(
            @PathVariable Long submissionId
    ) {
        return ResponseEntity.ok(submissionService.findSubmissionById(submissionId));
    }

    @PostMapping("/{submissionId}/assign")
    public ResponseEntity<String> assignSubmissionToEvaluator(
            @PathVariable Long submissionId,
            @RequestParam Long evaluatorId,
            @RequestParam Long editorId
    ) {
        submissionService.assignSubmissionToEvaluator(submissionId, evaluatorId, editorId);
        return ResponseEntity.ok("Submission successfully assigned to evaluator.");
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