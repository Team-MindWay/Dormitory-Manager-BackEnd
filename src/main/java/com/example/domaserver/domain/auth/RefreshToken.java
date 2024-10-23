package com.example.domaserver.domain.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash(value = "refreshToken", timeToLive = 60)
@Builder
@Getter
public class RefreshToken {
    @Id
    private String refreshToken;
    @Indexed
    private UUID memberId;
    private LocalDateTime expiredAt;
}
