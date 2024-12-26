package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.services.interfaces.ConferenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    @Override
    public List<Conference> findAllConferences() {
        return null;
    }
}
