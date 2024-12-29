package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class EvaluationController {
  private EvaluationService evaluationService;

  @Autowired
  public EvaluationController(EvaluationService evaluationService) {
      this.evaluationService = evaluationService;
  }

  @GetMapping("/evaluations/{evaluationId}")
  public ResponseEntity<Evaluation> getEvaluation(@PathVariable Long evaluationId) {
      try {
          Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);
          return ResponseEntity.ok(evaluation);
      } catch (ResourceNotFoundException e) {
          return ResponseEntity.notFound().build();
      }
  }

  @PostMapping("/addEvaluation")
  public ResponseEntity<Evaluation> createEvaluation(@RequestBody @Valid Evaluation newEvaluation) {
      Evaluation savedEvaluation = evaluationService.createEvaluation(newEvaluation);
      return ResponseEntity.ok(savedEvaluation);
  }
}
