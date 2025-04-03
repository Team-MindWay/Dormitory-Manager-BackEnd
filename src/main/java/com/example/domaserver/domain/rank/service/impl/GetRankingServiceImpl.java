package com.example.domaserver.domain.rank.service.impl;

import com.example.domaserver.domain.rank.service.GetRankingService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.global.annotation.ServiceWithReadOnlyTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@ServiceWithReadOnlyTransactional
@RequiredArgsConstructor
public class GetRankingServiceImpl implements GetRankingService {
    private static final String GET_RANKING = "getRank";

    private final RedisTemplate redisTemplate;

    public Long getRanking(User user) {
        Long rank = redisTemplate.opsForZSet().reverseRank(GET_RANKING, user.getId().toString());
        return (rank != null) ? rank + 1 : null;
    }
}
