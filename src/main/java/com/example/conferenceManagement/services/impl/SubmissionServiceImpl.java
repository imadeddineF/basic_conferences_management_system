package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.EvaluationRepository;
import com.example.conferenceManagement.repositories.SubmissionRepository;
import com.example.conferenceManagement.repositories.UserRepository;
import com.example.conferenceManagement.repositories.UserRoleRepository;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private SubmissionRepository submissionRepository;
    private EvaluationRepository evaluationRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public SubmissionServiceImpl(SubmissionRepository submissionRepository, EvaluationRepository evaluationRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.submissionRepository = submissionRepository;
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<Submission> findAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions;
    }

    @Override
    public Submission findSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));
    }

    @Override
    public Submission createSubmission(Submission submission) {
        // Validate that all authors exist
        List<Long> authorIds = submission.getAuthors().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        List<User> existingUsers = userRepository.findAllById(authorIds);

        if (existingUsers.size() != authorIds.size()) {
            throw new IllegalArgumentException("One or more authors do not exist in the database.");
        }

        // Save the submission
        return submissionRepository.save(submission);
    }


    @Override
    public void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId) {
        // Fetch submission, evaluator, and editor
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));

        User evaluator = userRepository.findById(evaluatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with id: " + evaluatorId));

        User editor = userRepository.findById(editorId)
                .orElseThrow(() -> new ResourceNotFoundException("Editor not found with id: " + editorId));

        // Verify that the editor is authorized (has the "EDITOR" role in the conference)
        boolean isEditor = userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(editorId, submission.getConference().getId(), EUserRole.EDITOR);
        if (!isEditor) {
            throw new IllegalArgumentException("User is not authorized as an editor for this conference.");
        }

        // Verify that the evaluator is not an author of the submission
        if (submission.getAuthors().contains(evaluator)) {
            throw new IllegalArgumentException("Evaluator cannot evaluate a submission they authored.");
        }

        // Assign the submission to the evaluator
        Evaluation evaluation = Evaluation.builder()
                .submission(submission)
                .reviewer(evaluator)
                .status(ESubmissionStatus.PENDING)
                .build();

        // Add the evaluation to the submission
        submission.getEvaluations().add(evaluation);

        // Save the updated submission
        submissionRepository.save(submission);
    }


}
