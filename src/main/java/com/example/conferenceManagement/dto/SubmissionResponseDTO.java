package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SubmissionResponseDTO {
    private Long id;
    private String title;
    private String summary;
    private String pdfUrl;
    private ESubmissionStatus status;
    private ConferenceResponseDTO conference;
    private List<Long> authorIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}