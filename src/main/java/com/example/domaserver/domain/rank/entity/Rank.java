package com.example.domaserver.domain.rank.entity;

import com.example.domaserver.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rank_table")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    private int penaltyPoints;
    private double rankScore;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "CHAR(36)")
    private User user;

    public Rank(Long rankId, User user, Double rankScore) {
        this.rankId = rankId;
        this.user = user;
        this.rankScore = rankScore;
    }
}
