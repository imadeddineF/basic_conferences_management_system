package com.example.conferenceManagement.services.impl;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.entities.UserRole;
import com.example.conferenceManagement.enums.EUserRole;
import com.example.conferenceManagement.repositories.UserRoleRepository;
import com.example.conferenceManagement.services.interfaces.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void assignRoleToUser(User user, Conference conference, EUserRole role) {
        UserRole.UserRoleId id = new UserRole.UserRoleId(
                user.getId(),
                conference.getId(),
                role
        );

        UserRole userRole = UserRole.builder()
                .id(id)
                .user(user)
                .conference(conference)
                .build();

        userRoleRepository.save(userRole);
    }
}