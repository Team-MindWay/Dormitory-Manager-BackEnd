package com.example.domaserver.domain.user.service;

import com.example.domaserver.domain.user.entity.User;

import java.util.UUID;

public interface UserService {
    User findByUsername(String name);
    User findById(UUID id);
}
