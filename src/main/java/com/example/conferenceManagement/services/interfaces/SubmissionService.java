package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.dto.SubmissionRequestDTO;
import com.example.conferenceManagement.dto.SubmissionResponseDTO;
import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.enums.ESubmissionStatus;

import java.util.List;

public interface SubmissionService {
    List<SubmissionResponseDTO> findAllSubmissions();
    SubmissionResponseDTO findSubmissionById(Long submissionId);
    SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionRequest);
    void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId);
}
