package com.example.domaserver.domain.rank.service.impl;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.service.GetTopRankingService;
import com.example.domaserver.global.annotation.ServiceWithReadOnlyTransactional;
import com.example.domaserver.global.annotation.ServiceWithTransaction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@ServiceWithTransaction
@ServiceWithReadOnlyTransactional
public class GetTopRankingServiceImpl implements GetTopRankingService {

    private static final String GET_TOP_RANKING = "getTopRanking";

    private final RedisTemplate redisTemplate;

    public GetTopRankingServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<Rank> getTopRanking(int topN) {
        Set<ZSetOperations.TypedTuple<Long>> rankedUsers =
                redisTemplate.opsForZSet().reverseRange(GET_TOP_RANKING, 0, topN - 1);

        List<Rank> userRanks = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Long> entry : rankedUsers) {
            Long RankId = entry.getValue();
            Double RankScore = entry.getScore();
            userRanks.add(new Rank(RankId, RankScore));
        }

        return userRanks;
    }

}
