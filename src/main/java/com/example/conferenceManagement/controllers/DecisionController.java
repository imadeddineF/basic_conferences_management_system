package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.services.interfaces.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ESubmissionStatus getConferenceDecisionById(@PathVariable Long conferenceId) {
        return decisionService.findConferenceDecisionById(conferenceId);
    }
}
