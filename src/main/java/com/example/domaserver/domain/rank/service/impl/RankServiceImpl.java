package com.example.domaserver.domain.rank.service.impl;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.service.RankService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@ServiceWithTransaction
public class RankServiceImpl implements RankService {

    private static final String RANK_KEY = "USER_RANKING";

    private final RedisTemplate redisTemplate;

    public RankServiceImpl(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public Long getRanking(User user) {
        Long rank = redisTemplate.opsForZSet().rank(RANK_KEY, user.getId());
        return (rank != null) ? rank + 1 : null;
    }

    @Transactional
    public void updateRank(Rank rank) {
        redisTemplate.opsForZSet().add(RANK_KEY, rank.getUser().getId(), rank.getPenaltyPoints());
    }

    @Transactional
    public List<Rank> getTopRanking(int topN) {
        Set<ZSetOperations.TypedTuple<Long>> rankedUsers =
                redisTemplate.opsForZSet().reverseRangeWithScores(RANK_KEY, 0, topN - 1);

        List<Rank> userRanks = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Long> entry : rankedUsers) {
            Long RankId = entry.getValue();
            Double RankScore = entry.getScore();
            userRanks.add(new Rank(RankId, RankScore));
        }
        return userRanks;
    }

}
