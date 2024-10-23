package com.example.domaserver.domain.user.util;

import com.example.domaserver.domain.auth.exception.UserNotFoundException;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
