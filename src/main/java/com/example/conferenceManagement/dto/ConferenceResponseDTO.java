package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.EConferenceStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ConferenceResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String theme;
    private EConferenceStatus status;
}