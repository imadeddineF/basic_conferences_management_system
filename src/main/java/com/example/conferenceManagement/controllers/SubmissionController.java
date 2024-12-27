package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.services.interfaces.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SubmissionController {
  SubmissionService submissionService;

  @Autowired
  public SubmissionController(SubmissionService submissionService) {
      this.submissionService = submissionService;
  }
}
