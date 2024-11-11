package com.example.domaserver.domain.rank.entity;

import com.example.domaserver.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long RankId;
    private int PenaltyPoints;
    private double RankScore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rank(Long rankId, Double rankScore) {
        RankId = rankId;
        RankScore = rankScore;
    }
}
