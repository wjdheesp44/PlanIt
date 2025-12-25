package com.gt.planit.domain.spot.model.mapper;

import com.gt.planit.domain.spot.model.dto.SpotDetailResDto;
import com.gt.planit.domain.spot.model.dto.SpotRes;
import com.gt.planit.domain.spot.model.dto.SpotSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpotMapper {
    /**
     * 스팟 리스트 조회 (필터 + 페이징) : 로그인 없는 버전
     * @param cond   검색 조건
     * @param offset LIMIT OFFSET
     * @param limit  LIMIT 개수
     * @param userId 즐겨찾기 여부 계산용 (null이면 isFavorite은 모두 false 처리해도 됨)
     */
    List<SpotRes> searchSpots(
            @Param("cond") SpotSearchCondition cond,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    /**
     * 스팟 전체 개수 (페이징 totalElements)
     */
    long countSpots(@Param("cond") SpotSearchCondition cond);

    List<Long> selectLikedSpotIds(@Param("spotIds") List<Long> spotIds, @Param("userId") Long userId);

    // 스팟 세부 페이지 조회
    SpotDetailResDto selectSpotDetailById(Long spotId);

    boolean checkSpotLike(@Param("spotId") Long spotId, @Param("userId") Long userId);

    List<SpotRes> selectRandomHotSpotsBySido(
            @Param("sidoId") Long sidoId,
            @Param("excludeSpotId") Long excludeSpotId,
            @Param("limit") int limit
    );

    void insertSpotView(Long spotId, Long userId);
}
