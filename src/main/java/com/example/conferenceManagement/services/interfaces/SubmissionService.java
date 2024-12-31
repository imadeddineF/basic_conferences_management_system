package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Submission;

import java.util.List;

public interface SubmissionService {
    List<Submission> findAllSubmissions();
    Submission findSubmissionById(Long submissionId);
    Submission createSubmission(Submission submission);
    void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId);
}
