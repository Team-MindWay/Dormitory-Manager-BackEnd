package com.example.domaserver.domain.rank.presentation;

import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.presentation.dto.response.RankResponse;
import com.example.domaserver.domain.rank.service.GetRankingService;
import com.example.domaserver.domain.rank.service.GetTopRankingService;
import com.example.domaserver.domain.rank.service.UpdateRankingService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.service.UserService;
import com.example.domaserver.global.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class RankController {
    private final GetRankingService getRankingService;
    private final GetTopRankingService getTopRankingService;
    private final UpdateRankingService updateRankingService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/rank")
    public ResponseEntity<List<RankResponse>> getRanks() {
        List<Rank> topRanks = getTopRankingService.getTopRanking(25);
        return ResponseEntity.ok(toRankResponses(topRanks));
    }

    @GetMapping("/my-rank")
    public ResponseEntity<Long> getMyRank(@RequestHeader("Authorization") String token) {
        User user = jwtService.getUserFromToken(token.substring(7));
        Long rank = getRankingService.getRanking(user);
        return ResponseEntity.ok(rank);
    }

    private List<RankResponse> toRankResponses(List<Rank> ranks) {
        return ranks.stream()
                .map(this::toRankResponse)
                .collect(Collectors.toList());
    }

    private RankResponse toRankResponse(Rank rank) {
        return RankResponse.builder()
                .Id(rank.getUser().getId())
                .name(rank.getUser().getUsername())
                .penaltyPoints(rank.getPenaltyPoints())
                .RankScore(rank.getRankScore())
                .build();
    }
}
