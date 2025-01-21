package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.ESubmissionStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitEvaluationDTO {
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 10, message = "Score must be at most 10")
    private int score;

    @NotBlank(message = "Comment cannot be blank")
    private String comment;

    @NotNull
    private ESubmissionStatus status;
}