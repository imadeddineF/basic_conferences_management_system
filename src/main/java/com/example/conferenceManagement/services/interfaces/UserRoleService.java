package com.example.conferenceManagement.services.interfaces;

import com.example.conferenceManagement.entities.Conference;
import com.example.conferenceManagement.entities.User;
import com.example.conferenceManagement.enums.EUserRole;

public interface UserRoleService {
    void assignRoleToUser(User user, Conference conference, EUserRole role);
}
