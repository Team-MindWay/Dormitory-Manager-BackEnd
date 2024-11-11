package com.example.domaserver.domain.rank.presentation;


import com.example.domaserver.domain.rank.entity.Rank;
import com.example.domaserver.domain.rank.service.RankService;
import com.example.domaserver.domain.user.entity.User;
import com.example.domaserver.domain.user.service.UserService;
import com.example.domaserver.global.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class RankController {
    private final RankService rankService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public RankController(RankService rankService, UserService userService, JwtService jwtService) {
        this.rankService = rankService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/rank")
    public ResponseEntity<List<Rank>> getRanks() {
        List<Rank> topRanks = rankService.getTopRanking(25);
        return ResponseEntity.ok(topRanks);
    }

    @GetMapping("/my-rank")
    public ResponseEntity<Long> getMyRank(@RequestHeader("Authorization") String token) {
        User user = jwtService.getUserFromToken(token.substring(7));
        Long rank = rankService.getRanking(user);
        return ResponseEntity.ok(rank);
    }


}
