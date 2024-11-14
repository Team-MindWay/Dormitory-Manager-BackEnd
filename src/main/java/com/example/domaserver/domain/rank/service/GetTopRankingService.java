package com.example.domaserver.domain.rank.service;

import com.example.domaserver.domain.rank.entity.Rank;

import java.util.List;

public interface GetTopRankingService {
    List<Rank> getTopRanking(int topN);
}
