package com.example.domaserver.domain.rank.service.impl;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.service.GetTopRankingService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.repository.UserRepository;
import com.example.domaserver.global.annotation.ServiceWithReadOnlyTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ServiceWithReadOnlyTransactional
@RequiredArgsConstructor
public class GetTopRankingServiceImpl implements GetTopRankingService {
    private static final String GET_TOP_RANKING = "getTopRanking";

    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;

    public List<Rank> getTopRanking(int topN) {
        Set<ZSetOperations.TypedTuple<String>> rankedUsers =
                redisTemplate.opsForZSet().reverseRangeWithScores(GET_TOP_RANKING, 0, topN - 1);

        List<UUID> userIds = rankedUsers.stream()
                .map(entry -> UUID.fromString(entry.getValue()))
                .collect(Collectors.toList());

        Map<UUID, User> userMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        List<Rank> userRanks = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> entry : rankedUsers) {
            UUID userId = UUID.fromString(entry.getValue());
            Double rankScore = entry.getScore();
            User user = userMap.get(userId);

            if (user != null) {
                userRanks.add(new Rank(null, user, rankScore));
            }
        }

        return userRanks;
    }
}
