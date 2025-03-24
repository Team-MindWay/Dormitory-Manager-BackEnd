package com.example.domaserver.domain.user.service.impl;

import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.exception.NotFoundException;
import com.example.domaserver.domain.user.repository.UserRepository;
import com.example.domaserver.domain.user.service.UserService;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransaction
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + name));
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

}
