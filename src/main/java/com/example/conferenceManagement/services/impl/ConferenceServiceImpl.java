package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.services.interfaces.ConferenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
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
    public Conference createConference(Conference newConference) {
        // Create User entity from Conference
        Conference conference = new Conference();
        conference.setTitle(newConference.getTitle());
        conference.setStartDate(newConference.getStartDate());
        conference.setEndDate(newConference.getEndDate());
        conference.setStatus(newConference.getStatus());
        conference.setTheme(newConference.getTheme());

        Conference savedConference = conferenceRepository.save(conference);

        return savedConference;
    }
}
