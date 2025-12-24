package com.gt.planit.domain.rank.controller;

import com.gt.planit.domain.rank.model.dto.SpotRankDto;
import com.gt.planit.domain.rank.model.service.RankService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/spots/ranking")
public class RankController {

    private final RankService spotService;

    @GetMapping("/main")
    public List<SpotRankDto> getSpotRanking(
            @RequestParam(defaultValue = "5") int limit,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = (user == null) ? null : user.getId();
        return spotService.getRankedSpots(limit, userId);
    }

    @GetMapping("/popup")
    public List<SpotRankDto> getPopupRanking(
            @RequestParam(defaultValue = "5") int limit,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = (user == null) ? null : user.getId();
        return spotService.getRankedPopup(limit, userId);
    }
}
