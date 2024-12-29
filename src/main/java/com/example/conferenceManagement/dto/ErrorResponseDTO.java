package com.example.conferenceManagement.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponseDTO {
        private int status;
        private String message;
        private Map<String, String> details;
}

