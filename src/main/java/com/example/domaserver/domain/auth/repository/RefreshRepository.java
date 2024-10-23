package com.example.domaserver.domain.auth.repository;

import com.example.domaserver.domain.auth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberId(UUID memberId);
}
