package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.enums.EConferenceStatus;

public interface DecisionService {
    EConferenceStatus findConferenceDecisionById(Long conferenceId);
}
