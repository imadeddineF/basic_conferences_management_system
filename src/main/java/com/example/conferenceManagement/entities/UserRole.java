package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.EUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(UserRole.class)
@Table(name = "user_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("conferenceId")
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUserRole role; // EDITOR, AUTHOR, REVIEWER

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class UserRoleId implements Serializable {
        private Long userId;
        private Long conferenceId;
    }
}

