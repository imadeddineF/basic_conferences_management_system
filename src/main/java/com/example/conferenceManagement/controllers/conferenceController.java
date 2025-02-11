package com.example.conferenceManagement.controllers;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.ConferenceService;
import com.example.conferenceManagement.services.interfaces.UserRoleService;
import com.example.conferenceManagement.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class conferenceController {
    private final ConferenceService conferenceService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Autowired
    public conferenceController(ConferenceService conferenceService, UserRoleService userRoleService, UserService userService) {
        this.conferenceService = conferenceService;
        this.userRoleService = userRoleService;
        this.userService = userService;
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
    public Conference createConference(
            @RequestBody @Valid Conference newConference,
            @RequestParam Long creatorId // Add creator ID as request parameter
    ) {
        User creator = userService.findUserEntityById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + creatorId));

        return conferenceService.createConference(newConference, creator);
    }

    @PostMapping("/conferences/{conferenceId}/editors/{userId}")
    public ResponseEntity<String> assignEditorRole(
            @PathVariable Long conferenceId,
            @PathVariable Long userId
    ) {
        User user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Conference conference = conferenceService.findConferenceById(conferenceId);
        userRoleService.assignRoleToUser(user, conference, EUserRole.EDITOR);
        return ResponseEntity.ok("EDITOR role assigned successfully");
    }

    @PostMapping("/conferences/{conferenceId}/authors/{userId}")
    public ResponseEntity<String> assignAuthorRole(
            @PathVariable Long conferenceId,
            @PathVariable Long userId
    ) {
        User user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Conference conference = conferenceService.findConferenceById(conferenceId);
        userRoleService.assignRoleToUser(user, conference, EUserRole.AUTHOR);
        return ResponseEntity.ok("AUTHOR role assigned successfully");
    }

    @PostMapping("/conferences/{conferenceId}/reviewers/{userId}")
    public ResponseEntity<String> assignReviewerRole(
            @PathVariable Long conferenceId,
            @PathVariable Long userId
    ) {
        User user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Conference conference = conferenceService.findConferenceById(conferenceId);
        userRoleService.assignRoleToUser(user, conference, EUserRole.REVIEWER);
        return ResponseEntity.ok("REVIEWER role assigned successfully");
    }


}
