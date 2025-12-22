package com.gt.planit.domain.likes.model.mapper;

import com.gt.planit.domain.likes.model.dto.MySpotLikeResDto;
import com.gt.planit.domain.likes.model.dto.SpotLikeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpotLikeMapper {

    SpotLikeDto findByUserAndSpot(
            @Param("userId") Long userId,
            @Param("spotId") Long spotId
    );

    List<MySpotLikeResDto> findLikedSpotsByUserId(Long userId);

    int insertLike(
            @Param("userId") Long userId,
            @Param("spotId") Long spotId
    );

    int updateLike(
            @Param("id") Long id,
            @Param("isDeleted") String isDeleted
    );

    int countLikes(@Param("spotId") Long spotId);

    boolean existsLiked(
            @Param("userId") Long userId,
            @Param("spotId") Long spotId
    );
}
