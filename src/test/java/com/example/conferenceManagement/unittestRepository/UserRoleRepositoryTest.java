package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.entities.UserRole;

import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.repositories.ConferenceRepository;
import com.example.conferenceManagement.repositories.UserRepository;
import com.example.conferenceManagement.repositories.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1, user2;
    private Conference conference1, conference2;

    @BeforeEach
    public void setUp() {
        // Create and save some Users
        user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password123");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("password456");

        userRepository.save(user1);
        userRepository.save(user2);

        // Create and save some Conferences
        conference1 = new Conference();
        conference1.setId(1L);
        conference1.setTitle("AI Revolution 2025");
        conferenceRepository.save(conference1);

        conference2 = new Conference();
        conference2.setId(2L);
        conference2.setTitle("Green Tech Summit");
        conferenceRepository.save(conference2);

        // Create and save some UserRoles
        UserRole.UserRoleId roleId1 = new UserRole.UserRoleId(1L, 1L, EUserRole.EDITOR);
        UserRole userRole1 = new UserRole(roleId1, user1, conference1);
        userRoleRepository.save(userRole1);

        UserRole.UserRoleId roleId2 = new UserRole.UserRoleId(2L, 2L, EUserRole.REVIEWER);
        UserRole userRole2 = new UserRole(roleId2, user2, conference2);
        userRoleRepository.save(userRole2);
    }

    @Test
    public void testExistsByIdUserIdAndIdConferenceIdAndIdRole() {
        // Test if the UserRole exists for userId 1, conferenceId 1, and role EDITOR
        boolean exists = userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(1L, 1L, EUserRole.EDITOR);
        assertTrue(exists);

        // Test if the UserRole exists for userId 2, conferenceId 2, and role REVIEWER
        boolean existsReviewer = userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(2L, 2L, EUserRole.REVIEWER);
        assertTrue(existsReviewer);

        // Test if the UserRole does not exist for a non-existent role
        boolean existsInvalid = userRoleRepository.existsByIdUserIdAndIdConferenceIdAndIdRole(1L, 2L, EUserRole.REVIEWER);
        assertFalse(existsInvalid);
    }
}
