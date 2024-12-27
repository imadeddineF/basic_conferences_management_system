package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.enums.ESubmissionStatus;

public interface DecisionService {
    ESubmissionStatus findConferenceDecisionById(Long conferenceId);
}
