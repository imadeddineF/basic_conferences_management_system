package com.example.conferenceManagement.unittestRepository;

package com.example.conferenceManagement.unittestRepository;

import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize a test user
        testUser = new User();
        testUser.setFirstName("Alice");
        testUser.setLastName("Smith");
        testUser.setEmail("alice@example.com");
        testUser.setRoles(List.of(EUserRole.AUTHOR, EUserRole.REVIEWER));

        // Save the test user to the repository
        userRepository.save(testUser);
    }

    @Test
    void testFindByRoles() {
        // Query using a specific role
        Optional<User> user = userRepository.findByRoles(List.of(EUserRole.REVIEWER));
        assertThat(user).isPresent();
        assertThat(user.get().getRoles()).contains(EUserRole.REVIEWER);
    }

    @Test
    void testFindByRolesWithNonExistentRole() {
        // Query with a role not assigned to any user
        Optional<User> user = userRepository.findByRoles(List.of(EUserRole.EDITOR));
        assertThat(user).isNotPresent();
    }
}
