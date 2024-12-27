package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.EUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("conferenceId")
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUserRole role;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class UserRoleId implements Serializable {
        private Long userId;
        private Long conferenceId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleId that = (UserRoleId) o;
            return Objects.equals(userId, that.userId) &&
                    Objects.equals(conferenceId, that.conferenceId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, conferenceId);
        }
    }
}