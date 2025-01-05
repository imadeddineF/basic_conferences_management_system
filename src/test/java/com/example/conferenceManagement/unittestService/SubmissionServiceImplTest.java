package com.example.conferenceManagement.unittestService;

import com.example.conferenceManagement.entities.*;
import com.example.conferenceManagement.enums.ESubmissionStatus;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.repositories.*;
import com.example.conferenceManagement.services.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubmissionServiceImplTest {

    @InjectMocks
    private SubmissionServiceImpl submissionService;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllSubmissions_Success() {
        // Arrange
        Submission submission1 = Submission.builder().id(1L).title("Title1").build();
        Submission submission2 = Submission.builder().id(2L).title("Title2").build();
        when(submissionRepository.findAll()).thenReturn(Arrays.asList(submission1, submission2));

        // Act
        List<Submission> submissions = submissionService.findAllSubmissions();

        // Assert
        assertEquals(2, submissions.size());
        verify(submissionRepository, times(1)).findAll();
    }

    @Test
    void testFindSubmissionById_Success() {
        // Arrange
        Long submissionId = 1L;
        Submission submission = Submission.builder().id(submissionId).title("Title1").build();
        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(submission));

        // Act
        Submission result = submissionService.findSubmissionById(submissionId);

        // Assert
        assertNotNull(result);
        assertEquals(submissionId, result.getId());
        verify(submissionRepository, times(1)).findById(submissionId);
    }

    @Test
    void testFindSubmissionById_NotFound() {
        // Arrange
        Long submissionId = 1L;
        when(submissionRepository.findById(submissionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> submissionService.findSubmissionById(submissionId));
        verify(submissionRepository, times(1)).findById(submissionId);
    }

    @Test
    void testCreateSubmission_Success() {
        // Arrange
        User author1 = User.builder().id(1L).build();
        User author2 = User.builder().id(2L).build();
        Submission submission = Submission.builder()
                .id(1L)
                .title("New Submission")
                .authors(Arrays.asList(author1, author2))
                .build();
        when(userRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(author1, author2));
        when(submissionRepository.save(submission)).thenReturn(submission);

        // Act
        Submission result = submissionService.createSubmission(submission);

        // Assert
        assertNotNull(result);
        assertEquals("New Submission", result.getTitle());
        verify(userRepository, times(1)).findAllById(Arrays.asList(1L, 2L));
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void testCreateSubmission_AuthorNotFound() {
        // Arrange
        User author1 = User.builder().id(1L).build();
        Submission submission = Submission.builder()
                .id(1L)
                .title("New Submission")
                .authors(Arrays.asList(author1))
                .build();
        when(userRepository.findAllById(Arrays.asList(1L))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> submissionService.createSubmission(submission));
        verify(userRepository, times(1)).findAllById(Arrays.asList(1L));
        verify(submissionRepository, never()).save(any());
    }

    @Test
    void testAssignSubmissionToEvaluator_Success() {
        // Arrange
        Long submissionId = 1L;
        Long evaluatorId = 2L;
        Long editorId = 3L;

        Conference conference = Conference.builder().id(100L).build();

        Submission submission = Submission.builder()
                .id(submissionId)
                .conference(conference)
                .authors(new ArrayList<>())
                .evaluations(new ArrayList<>())
                .build();

        User evaluator = User.builder().id(evaluatorId).build();
        User editor = User.builder().id(editorId).build();

        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(submission));
        when(userRepository.findById(evaluatorId)).thenReturn(Optional.of(evaluator));
        when(userRepository.findById(editorId)).thenReturn(Optional.of(editor));
        when(userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(editorId, conference.getId(), EUserRole.EDITOR))
                .thenReturn(true);

        // Act
        submissionService.assignSubmissionToEvaluator(submissionId, evaluatorId, editorId);

        // Assert
        ArgumentCaptor<Submission> captor = ArgumentCaptor.forClass(Submission.class);
        verify(submissionRepository).save(captor.capture());

        Submission savedSubmission = captor.getValue();
        assertEquals(1, savedSubmission.getEvaluations().size());

        Evaluation evaluation = savedSubmission.getEvaluations().get(0);
        assertEquals(submission, evaluation.getSubmission());
        assertEquals(evaluator, evaluation.getReviewer());
        assertEquals(ESubmissionStatus.PENDING, evaluation.getStatus());
    }


    @Test
    void testAssignSubmissionToEvaluator_EditorNotAuthorized() {
        // Arrange
        Long submissionId = 1L, evaluatorId = 2L, editorId = 3L;
        Submission submission = Submission.builder()
                .id(submissionId)
                .conference(Conference.builder().id(5L).build())
                .build();
        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(submission));
        when(userRepository.findById(evaluatorId)).thenReturn(Optional.of(User.builder().id(evaluatorId).build()));
        when(userRepository.findById(editorId)).thenReturn(Optional.of(User.builder().id(editorId).build()));
        when(userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(editorId, 5L, EUserRole.EDITOR)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> submissionService.assignSubmissionToEvaluator(submissionId, evaluatorId, editorId));
        verify(userRoleRepository, times(1)).existsByIdUserIdAndIdConferenceIdAndIdRole(editorId, 5L, EUserRole.EDITOR);
    }
}
