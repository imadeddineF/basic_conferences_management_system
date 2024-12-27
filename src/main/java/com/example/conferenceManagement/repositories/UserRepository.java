package com.example.conferenceManagement.repositories;

import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByEmail(String email);
    Optional<User> findByRoles(List<UserRole> roles);
}
