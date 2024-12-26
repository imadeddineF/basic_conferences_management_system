package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.UserDTO;
import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;

import java.util.List;

public interface ConferenceService {
    List<Conference> findAllConferences();
    Conference findConferenceById(Long conferenceId);
    Conference createConference(Conference newConference);
}
