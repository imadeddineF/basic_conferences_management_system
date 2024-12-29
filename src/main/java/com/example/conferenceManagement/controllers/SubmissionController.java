package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class SubmissionController {
  SubmissionService submissionService;

  @Autowired
  public SubmissionController(SubmissionService submissionService) {
      this.submissionService = submissionService;
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
