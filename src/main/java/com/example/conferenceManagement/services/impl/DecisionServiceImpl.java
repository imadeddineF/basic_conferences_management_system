package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.EConferenceStatus;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.services.interfaces.DecisionService;
import org.springframework.stereotype.Service;

@Service
public class DecisionServiceImpl implements DecisionService {
    private ConferenceRepository conferenceRepository;

    public DecisionServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public EConferenceStatus findConferenceDecisionById(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Conference not found with id: " + conferenceId));
        return conference.getStatus();
    }
}
