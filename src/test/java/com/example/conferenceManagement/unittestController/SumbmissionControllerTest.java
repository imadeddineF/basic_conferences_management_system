package com.example.conferenceManagement.unittestController;

import com.example.conferenceManagement.controllers.SubmissionController;
import com.example.conferenceManagement.entities.Submission;
import com.example.conferenceManagement.exceptions.ResourceNotFoundException;
import com.example.conferenceManagement.services.interfaces.SubmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SumbmissionControllerTest {

    @Mock
    private SubmissionService submissionService;

    @InjectMocks
    private SubmissionController submissionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(submissionController).build();
    }

    @Test
    public void testGetAllSubmissions() throws Exception {
        // Arrange
        Submission submission = new Submission();
        submission.setId(1L);
        submission.setTitle("Test Submission");
        when(submissionService.findAllSubmissions()).thenReturn(Collections.singletonList(submission));

        // Act & Assert
        mockMvc.perform(get("/api/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Submission"));
    }

    @Test
    public void testCreateSubmission() throws Exception {
        // Arrange
        Submission newSubmission = new Submission();
        newSubmission.setTitle("New Submission");
        Submission savedSubmission = new Submission();
        savedSubmission.setId(1L);
        savedSubmission.setTitle("New Submission");

        when(submissionService.createSubmission(any(Submission.class))).thenReturn(savedSubmission);

        // Act & Assert
        mockMvc.perform(post("/api/addSubmission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Submission\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Submission"));
    }

    @Test
    public void testGetSubmissionById_Success() throws Exception {
        // Arrange
        Submission submission = new Submission();
        submission.setId(1L);
        submission.setTitle("Submission By ID");
        when(submissionService.findSubmissionById(1L)).thenReturn(submission);

        // Act & Assert
        mockMvc.perform(get("/api/submissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Submission By ID"));
    }

    @Test
    public void testGetSubmissionById_NotFound() throws Exception {
        // Arrange
        when(submissionService.findSubmissionById(1L)).thenThrow(new ResourceNotFoundException("Submission not found"));

        // Act & Assert
        mockMvc.perform(get("/api/submissions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAssignSubmissionToEvaluator_Success() throws Exception {
        // Arrange
        doNothing().when(submissionService).assignSubmissionToEvaluator(1L, 2L, 3L);

        // Act & Assert
        mockMvc.perform(post("/api/submissions/1/assign")
                        .param("evaluatorId", "2")
                        .param("editorId", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Submission successfully assigned to evaluator."));
    }

    @Test
    public void testAssignSubmissionToEvaluator_Failure() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Invalid evaluator or editor ID"))
                .when(submissionService).assignSubmissionToEvaluator(1L, 2L, 3L);

        // Act & Assert
        mockMvc.perform(post("/api/submissions/1/assign")
                        .param("evaluatorId", "2")
                        .param("editorId", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid evaluator or editor ID"));
    }
}

