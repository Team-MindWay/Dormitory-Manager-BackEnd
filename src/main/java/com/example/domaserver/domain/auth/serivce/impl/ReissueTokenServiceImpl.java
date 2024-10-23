package com.example.domaserver.domain.auth.serivce.impl;

import com.example.domaserver.domain.auth.RefreshToken;
import com.example.domaserver.domain.auth.exception.ExpiredRefreshTokenException;
import com.example.domaserver.domain.auth.exception.UserNotFoundException;
import com.example.domaserver.domain.auth.presentation.dto.response.TokenResponse;
import com.example.domaserver.domain.auth.repository.RefreshRepository;
import com.example.domaserver.domain.auth.serivce.ReissueTokenService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.repository.UserRepository;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import com.example.domaserver.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransaction
public class ReissueTokenServiceImpl implements ReissueTokenService {
    private final JwtTokenProvider jwtProvider;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

    public TokenResponse execute(String refreshToken) {
        String parseRefreshToken = jwtProvider.parseRefreshToken(refreshToken);

        RefreshToken refreshEntity = refreshRepository.findById(parseRefreshToken)
                .orElseThrow(ExpiredRefreshTokenException::new);

        User user = userRepository.findById(refreshEntity.getMemberId())
                .orElseThrow(UserNotFoundException::new);

        TokenResponse tokenResponse = jwtProvider.generateTokenDto(user.getId());

        saveRefreshToken(tokenResponse.getRefreshToken(), user.getId(), tokenResponse.getRefreshTokenExpiresIn());

        return tokenResponse;
    }

    private void saveRefreshToken(String refreshToken, UUID memberId, LocalDateTime expiredAt) {
        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .memberId(memberId)
                .expiredAt(expiredAt)
                .build();

        refreshRepository.save(token);
    }
}
