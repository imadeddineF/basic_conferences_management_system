package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.services.interfaces.ConferenceService;
import com.example.conferenceManagement.services.interfaces.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private ConferenceRepository conferenceRepository;
    private final UserRoleService userRoleService;

    public ConferenceServiceImpl(
            ConferenceRepository conferenceRepository,
            UserRoleService userRoleService
    ) {
        this.conferenceRepository = conferenceRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public List<Conference> findAllConferences() {
        List<Conference> conferences = conferenceRepository.findAll();
        return conferences;
    }

    @Override
    public Conference findConferenceById(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Conference not found with id: " + conferenceId));
        return conference;
    }

    @Override
    @Transactional
    public Conference createConference(Conference newConference, User creator) {
        // 1. Create and save the conference
        Conference conference = Conference.builder()
                .title(newConference.getTitle())
                .startDate(newConference.getStartDate())
                .endDate(newConference.getEndDate())
                .theme(newConference.getTheme())
                .status(EConferenceStatus.OPEN)
                .build();

        Conference savedConference = conferenceRepository.save(conference);

        // 2. Assign EDITOR role to the creator
        userRoleService.assignRoleToUser(creator, savedConference, EUserRole.EDITOR);

        return savedConference;
    }
}
