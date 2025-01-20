package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.dto.SubmissionRequestDTO;
import com.example.conferenceManagement.dto.SubmissionResponseDTO;
import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.Evaluation;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.*;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public SubmissionServiceImpl(
            SubmissionRepository submissionRepository,
            EvaluationRepository evaluationRepository,
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            ConferenceRepository conferenceRepository
    ) {
        this.submissionRepository = submissionRepository;
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<SubmissionResponseDTO> findAllSubmissions() {
        return submissionRepository.findAll()
                .stream()
                .map(this::mapToSubmissionResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO findSubmissionById(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));
        return mapToSubmissionResponseDTO(submission);
    }



    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionRequest) {
        // Fetch conference
        Conference conference = conferenceRepository.findById(submissionRequest.getConferenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Conference not found with id: " + submissionRequest.getConferenceId()));

        // Validate authors
        List<User> authors = userRepository.findAllById(submissionRequest.getAuthorIds());
        if (authors.size() != submissionRequest.getAuthorIds().size()) {
            throw new IllegalArgumentException("One or more authors do not exist.");
        }

        // Build submission entity
        Submission submission = Submission.builder()
                .title(submissionRequest.getTitle())
                .summary(submissionRequest.getSummary())
                .pdfUrl(submissionRequest.getPdfUrl())
                .status(ESubmissionStatus.valueOf(submissionRequest.getStatus().name()))
                .conference(conference)
                .authors(authors)
                .build();

        Submission savedSubmission = submissionRepository.save(submission);
        return mapToSubmissionResponseDTO(savedSubmission);
    }

    @Override
    public void assignSubmissionToEvaluator(Long submissionId, Long evaluatorId, Long editorId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));

        User evaluator = userRepository.findById(evaluatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with id: " + evaluatorId));

        User editor = userRepository.findById(editorId)
                .orElseThrow(() -> new ResourceNotFoundException("Editor not found with id: " + editorId));

        // Verify editor authorization
        boolean isEditor = userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(
                editorId,
                submission.getConference().getId(),
                EUserRole.EDITOR
        );
        if (!isEditor) {
            throw new IllegalArgumentException("User is not authorized as an editor for this conference.");
        }

        // Check if evaluator is an author
        if (submission.getAuthors().contains(evaluator)) {
            throw new IllegalArgumentException("Evaluator cannot evaluate a submission they authored.");
        }

        // Create evaluation with default values
        Evaluation evaluation = Evaluation.builder()
                .submission(submission)
                .reviewer(evaluator)
                .status(ESubmissionStatus.PENDING)
                .score(null)  // Explicitly set to null
                .comment("Pending evaluation")  // Default placeholder
                .build();

        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        submission.getEvaluations().add(savedEvaluation);
        submissionRepository.save(submission);
    }

    // Convert entity to DTO
    private SubmissionResponseDTO mapToSubmissionResponseDTO(Submission submission) {
        return SubmissionResponseDTO.builder()
                .id(submission.getId())
                .title(submission.getTitle())
                .summary(submission.getSummary())
                .pdfUrl(submission.getPdfUrl())
                .status(submission.getStatus())
                .conferenceId(submission.getConference().getId())
                .authorIds(submission.getAuthors().stream().map(User::getId).collect(Collectors.toList()))
                .createdAt(submission.getCreatedAt())
                .updatedAt(submission.getUpdatedAt())
                .build();
    }
}
