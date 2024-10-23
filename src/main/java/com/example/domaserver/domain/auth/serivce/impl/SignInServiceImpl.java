package com.example.domaserver.domain.auth.serivce.impl;

import com.example.domaserver.domain.auth.RefreshToken;
import com.example.domaserver.domain.auth.exception.UserNotFoundException;
import com.example.domaserver.domain.auth.presentation.dto.request.SignInRequest;
import com.example.domaserver.domain.auth.presentation.dto.response.TokenResponse;
import com.example.domaserver.domain.auth.repository.RefreshRepository;
import com.example.domaserver.domain.auth.serivce.SignInService;
import com.example.domaserver.domain.user.entity.Authority;
import com.example.domaserver.domain.user.entity.StudentNum;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.repository.UserRepository;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import com.example.domaserver.global.security.jwt.JwtTokenProvider;
import gauth.GAuth;
import gauth.exception.GAuthException;
import gauth.response.GAuthToken;
import gauth.response.GAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;
import java.util.UUID;

@ServiceWithTransaction
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {
    private final GAuth gAuth;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;

    @Value("${gauth.clientId}")
    private String clientId;
    @Value("${gauth.clientSecret}")
    private String clientSecret;
    @Value("${gauth.redirectUri}")
    private String redirectUri;

    public TokenResponse execute(SignInRequest signInRequest) {
        try {
            GAuthToken gAuthToken = gAuth.generateToken(
                    signInRequest.getCode(),
                    clientId,
                    clientSecret,
                    redirectUri
            );

            GAuthUserInfo userInfo = gAuth.getUserInfo(gAuthToken.getAccessToken());

            User user = userRepository.findByEmail(userInfo.getEmail())
                    .orElseGet(() -> saveUser(userInfo));

            if (user == null)
                throw new UserNotFoundException();

            TokenResponse tokenResponse = jwtProvider.generateTokenDto(user.getId());

            saveRefreshToken(tokenResponse, user);

            return tokenResponse;

        } catch (GAuthException e) {
            throw new GAuthException(e.getCode());
        }

    }

    private User saveUser(GAuthUserInfo gAuthUserInfo) {
        if (Objects.equals(gAuthUserInfo.getRole(), "ROLE_STUDENT")) {
            return saveStudent(gAuthUserInfo);
        } else if (Objects.equals(gAuthUserInfo.getRole(), "ROLE_ADMIN")) {
            return saveAdmin(gAuthUserInfo);
        }
        return null;
    }

    private User saveStudent(GAuthUserInfo gAuthUserInfo) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email(gAuthUserInfo.getEmail())
                .name(gAuthUserInfo.getName())
                .studentNum(new StudentNum(gAuthUserInfo.getGrade(), gAuthUserInfo.getClassNum(), gAuthUserInfo.getNum()))
                .authority(Authority.ROLE_STUDENT)
                .build();

        userRepository.save(user);

        return user;
    }

    private User saveAdmin(GAuthUserInfo gAuthUserInfo) {
        User admin = User.builder()
                .id(UUID.randomUUID())
                .email(gAuthUserInfo.getEmail())
                .name(gAuthUserInfo.getName())
                .studentNum(new StudentNum(gAuthUserInfo.getGrade(), gAuthUserInfo.getClassNum(), gAuthUserInfo.getNum()))
                .authority(Authority.ROLE_ADMIN)
                .build();

        userRepository.save(admin);

        return admin;
    }

    private void saveRefreshToken(TokenResponse tokenResponse, User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(tokenResponse.getRefreshToken())
                .memberId(user.getId())
                .expiredAt(tokenResponse.getRefreshTokenExpiresIn())
                .build();

        refreshRepository.save(refreshToken);
    }
}
