package com.gt.planit.domain.spot.model.service;

import com.gt.planit.domain.spot.model.dto.SpotDetailResDto;
import com.gt.planit.domain.spot.model.dto.SpotRes;
import com.gt.planit.domain.spot.model.dto.SpotSearchCondition;
import com.gt.planit.domain.spot.model.mapper.SpotMapper;
import com.gt.planit.global.response.PageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotMapper spotMapper;

    @Override
    public PageRes<SpotRes> searchSpots(SpotSearchCondition condition, int page, int size, Long userId) {
        normalizeCondition(condition);
        int pageIndex = Math.max(page, 1) - 1;
        int offset = pageIndex * size;
        List<SpotRes> data = spotMapper.searchSpots(condition, offset, size);

        // 로그인한 경우 좋아요 여부 체크
        if (userId != null) {
            List<Long> spotIds = data.stream()
                    .map(SpotRes::getId)
                    .collect(Collectors.toList());

            if (!spotIds.isEmpty()) {
                List<Long> likedSpotIds = spotMapper.selectLikedSpotIds(spotIds, userId);
                Set<Long> likedSet = new HashSet<>(likedSpotIds);

                data.forEach(spot -> spot.setFavorite(likedSet.contains(spot.getId())));
            }
        }

        long totalElements = spotMapper.countSpots(condition);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean last = (pageIndex + 1) >= totalPages;

        return PageRes.<SpotRes>builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(last)
                .data(data)
                .build();
    }

    @Override
    public SpotDetailResDto searchSpotsById(Long spotId, Long userId) {
        // 1. 스팟 기본 정보 조회
        SpotDetailResDto spotDetail = spotMapper.selectSpotDetailById(spotId);

        if (spotDetail == null) {
            throw new RuntimeException("존재하지 않는 스팟입니다.");
        }

        // 2. 좋아요 여부 체크
        boolean isFavorite = false;
        if (userId != null) {
            isFavorite = spotMapper.checkSpotLike(spotId, userId);
        }
        spotDetail.setFavorite(isFavorite);

        // 3. 같은 시/도의 핫스팟 5개 랜덤 조회
        List<SpotRes> hotSpots = spotMapper.selectRandomHotSpotsBySido(spotDetail.getSidoId(), spotId, 5);
        spotDetail.setHotSpots(hotSpots);

        // 4. 조회수 증가
        spotMapper.insertSpotView(spotId);

        return spotDetail;
    }

    private void normalizeCondition(SpotSearchCondition condition) {

        // 1) 별점: null/빈 리스트/1~5 전체 선택이면 필터 해제
        List<Integer> ratings = condition.getRatings();
        if (ratings == null || ratings.isEmpty()) {
            condition.setRatings(null);
        } else {
            // 중복 제거 + 정렬
            List<Integer> distinctSorted = ratings.stream()
                    .distinct()
                    .sorted()
                    .toList();

            // 1~5 전체 선택인지 체크
            List<Integer> fullRatings = List.of(1, 2, 3, 4, 5);
            if (distinctSorted.equals(fullRatings)) {
                // 전체 선택이면 조건 안 거는 게 요구사항이므로 null 처리
                condition.setRatings(null);
            } else {
                condition.setRatings(distinctSorted);
            }
        }

        // 2) 타입: 아예 안 들어왔으면 전체 타입 허용으로 보고 null 처리
        if (condition.getTypes() == null || condition.getTypes().isEmpty()) {
            condition.setTypes(null);
        }
    }
}
