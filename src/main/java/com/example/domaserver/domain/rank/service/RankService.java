package com.example.domaserver.domain.rank.service;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.user.entity.User;
import java.util.List;

public interface RankService {
    Long getRanking(User user);
    void updateRank(Rank rank);
    List<Rank> getTopRanking(int topN);
}
