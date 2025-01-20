package com.example.conferenceManagement.entities;

import com.example.conferenceManagement.enums.EUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;  // Composite primary key containing userId, conferenceId, and role

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")  // Maps the userId field from the embedded ID
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("conferenceId")  // Maps the conferenceId field from the embedded ID
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class UserRoleId implements Serializable {

        @Column(name = "user_id")   // Explicitly define the column names for clarity
        private Long userId;

        @Column(name = "conference_id")  // Explicitly define the column names for clarity
        private Long conferenceId;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")  // Make sure the role is mapped correctly in the composite key
        private EUserRole role;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleId that = (UserRoleId) o;
            return Objects.equals(userId, that.userId) &&
                    Objects.equals(conferenceId, that.conferenceId) &&
                    role == that.role;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, conferenceId, role);
        }
    }
}
