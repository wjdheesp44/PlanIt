package com.gt.planit.domain.rank.model.service;

import com.gt.planit.domain.rank.model.dto.SpotRankDto;
import com.gt.planit.domain.rank.model.mapper.RankMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final RankMapper rankMapper;

    public List<SpotRankDto> getRankedSpots(int limit, Long userId) {
        return rankMapper.selectRankedSpots(limit, userId);
    }

    public List<SpotRankDto> getRankedPopup(int limit, Long userId) {
        return rankMapper.selectRankedPopup(limit, userId);
    }
}
