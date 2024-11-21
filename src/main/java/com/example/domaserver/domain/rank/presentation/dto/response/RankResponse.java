package com.example.domaserver.domain.rank.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class RankResponse {
    private UUID Id;
    private String name;
    private int penaltyPoints;
    private double RankScore;
}
