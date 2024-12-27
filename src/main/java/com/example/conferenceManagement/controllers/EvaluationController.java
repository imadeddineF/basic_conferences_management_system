package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.services.interfaces.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EvaluationController {
  private EvaluationService evaluationService;

  @Autowired
  public EvaluationController(EvaluationService evaluationService) {
      this.evaluationService = evaluationService;
  }
}
