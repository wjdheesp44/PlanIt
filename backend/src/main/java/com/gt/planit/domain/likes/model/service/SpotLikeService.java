package com.gt.planit.domain.likes.model.service;

import com.gt.planit.domain.likes.model.dto.SpotLikeDto;
import com.gt.planit.domain.likes.model.mapper.SpotLikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotLikeService {

    private final SpotLikeMapper spotLikeMapper;

    @Transactional
    public boolean toggleSpotLike(Long userId, Long spotId) {

        SpotLikeDto like = spotLikeMapper.findByUserAndSpot(userId, spotId);

        // 이미 기록 있음
        if (like != null) {
            if ("F".equals(like.getIsDeleted())) {
                spotLikeMapper.updateLike(like.getId(), "T");
                return false; // 좋아요 취소
            } else {
                spotLikeMapper.updateLike(like.getId(), "F");
                return true;  // 다시 좋아요
            }
        }

        // 처음 좋아요
        spotLikeMapper.insertLike(userId, spotId);
        return true;
    }

}
