package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.services.interfaces.ConferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class conferenceController {
    private ConferenceService conferenceService;

    @Autowired
    public conferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/conferences")
    public List<Conference> getAllConferences() {
        return conferenceService.findAllConferences();
    }

    @GetMapping("/conferences/{conferenceId}")
    public Conference getConferenceById(@PathVariable Long conferenceId) {
        return conferenceService.findConferenceById(conferenceId);
    }

    @PostMapping("/conferences/addConference")
    public Conference createConference(@RequestBody @Valid Conference newConference) {
        return conferenceService.createConference(newConference);
    }
}
