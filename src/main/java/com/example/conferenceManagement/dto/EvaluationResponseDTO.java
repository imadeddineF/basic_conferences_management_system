package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EvaluationResponseDTO {
    private Long id;
    private Integer score;
    private String comment;
    private ESubmissionStatus status;
    private Long submissionId; // Include submission ID instead of full object
    private Long reviewerId;   // Include reviewer ID instead of full user object
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}