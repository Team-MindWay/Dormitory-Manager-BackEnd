package com.example.domaserver.domain.rank.service.impl;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.service.UpdateRankingService;
import com.example.domaserver.global.annotation.ServiceWithReadOnlyTransactional;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import org.springframework.data.redis.core.RedisTemplate;


@ServiceWithTransaction
public class UpdateRankingServiceImpl implements UpdateRankingService {

    private static final String UPDATE_RANKING = "update ranking set rank=rank+1 where rank=?";

    private final RedisTemplate redisTemplate;

    public UpdateRankingServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void updateRanking(Rank rank) {
        redisTemplate.opsForSet().add(UPDATE_RANKING, rank.getUser().getId(), rank.getPenaltyPoints());
    }

}
