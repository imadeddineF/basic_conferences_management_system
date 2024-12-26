package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.Conference;

import java.util.List;

public interface ConferenceService {
    List<Conference> findAllConferences();
}
