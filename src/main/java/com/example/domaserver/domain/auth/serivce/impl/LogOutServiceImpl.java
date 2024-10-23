package com.example.domaserver.domain.auth.serivce.impl;

import com.example.domaserver.domain.auth.RefreshToken;
import com.example.domaserver.domain.auth.exception.ExpiredRefreshTokenException;
import com.example.domaserver.domain.auth.repository.RefreshRepository;
import com.example.domaserver.domain.auth.serivce.LogOutService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.util.UserUtil;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import com.example.domaserver.global.redis.RedisUtil;
import com.example.domaserver.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@ServiceWithTransaction
@RequiredArgsConstructor
public class LogOutServiceImpl implements LogOutService {
    private final RefreshRepository refreshRepository;
    private final UserUtil userUtil;
    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtProvider;

    public void execute(String accessToken) {
        User user = userUtil.getCurrentUser();

        RefreshToken validRefreshToken = refreshRepository.findByMemberId(user.getId())
                .orElseThrow(ExpiredRefreshTokenException::new);

        refreshRepository.deleteById(validRefreshToken.getRefreshToken());
        redisUtil.setBlackList(accessToken, "access_Token", jwtProvider.getExpiration(accessToken));
    }
}
