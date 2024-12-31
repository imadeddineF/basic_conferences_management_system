package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class SubmissionController {
  SubmissionService submissionService;

  @Autowired
  public SubmissionController(SubmissionService submissionService) {
      this.submissionService = submissionService;
  }

  @GetMapping("/submissions")
  public List<Submission> getAllSubmissions() {
      return this.submissionService.findAllSubmissions();
  }

  @PostMapping("/addSubmission")
  public ResponseEntity<Submission> createSubmission(@RequestBody @Valid Submission newSubmission) {
      Submission savedSubmission = submissionService.createSubmission(newSubmission);
      return ResponseEntity.ok(savedSubmission);
  }

  @GetMapping("/submissions/{submissionId}")
  public ResponseEntity<Submission> getSubmissionById(@PathVariable Long submissionId) {
      try {
          Submission submission = submissionService.findSubmissionById(submissionId);
          return ResponseEntity.ok(submission);
      } catch (ResourceNotFoundException e) {
          return ResponseEntity.notFound().build();
      }
  }

  @PostMapping("/submissions/{submissionId}/assign")
  public ResponseEntity<String> assignSubmissionToEvaluator(
          @PathVariable Long submissionId,
          @RequestParam Long evaluatorId,
          @RequestParam Long editorId
  ) {
      try {
          submissionService.assignSubmissionToEvaluator(submissionId, evaluatorId, editorId);
          return ResponseEntity.ok("Submission successfully assigned to evaluator.");
      } catch (IllegalArgumentException | ResourceNotFoundException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
}
