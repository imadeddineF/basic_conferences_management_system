package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.UserRole;
import com.example.conferenceManagement.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    boolean existsByUserIdAndConferenceIdAndRole(Long userId, Long conferenceId, EUserRole role);
}
