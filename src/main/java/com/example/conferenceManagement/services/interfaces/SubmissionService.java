package com.example.conferenceManagement.services.interfaces;

public interface SubmissionService {

    void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId);
}
