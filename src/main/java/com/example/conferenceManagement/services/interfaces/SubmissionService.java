package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.entities.Submission;

public interface SubmissionService {
    Submission findSubmissionById(Long submissionId);
    Submission createSubmission(Submission submission);
    void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId);
}
