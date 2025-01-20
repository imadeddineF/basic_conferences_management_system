package com.example.conferenceManagement.dto;

import com.example.conferenceManagement.enums.EUserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleInfoDTO {
    private Long conferenceId;
    private EUserRole role;
}
