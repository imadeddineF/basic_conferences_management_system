package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SubmissionRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Summary cannot be blank")
    private String summary;

    @NotBlank(message = "PDF URL cannot be blank")
    private String pdfUrl;

    @NotNull(message = "Conference ID cannot be null")
    private Long conferenceId;  // ID of the conference (not the full object)

    @NotNull(message = "Author IDs cannot be null")
    private List<Long> authorIds;  // IDs of authors (users)

    @NotNull(message = "Status cannot be null")
    private ESubmissionStatus status;  // PENDING, ACCEPTED, etc.
}