package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class DecisionController {
    private DecisionService decisionService;

    @Autowired
    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @GetMapping("/conferences/decision/{conferenceId}")
    public ResponseEntity<EConferenceStatus> getConferenceDecisionById(@PathVariable Long conferenceId) {
        try {
            EConferenceStatus status = decisionService.findConferenceDecisionById(conferenceId);
            return ResponseEntity.ok(status);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
