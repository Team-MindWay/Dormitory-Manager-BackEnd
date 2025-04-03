package com.example.domaserver.domain.rank.service;

import com.example.domaserver.domain.user.entity.User;

public interface GetRankingService {
    Long getRanking(User user);
}
