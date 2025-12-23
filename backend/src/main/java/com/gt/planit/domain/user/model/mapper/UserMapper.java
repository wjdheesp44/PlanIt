package com.gt.planit.domain.user.model.mapper;

import com.gt.planit.domain.plan.model.dto.SearchUserResDto;
import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.security.service.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    List<SearchUserResDto> searchUsers(@Param("nickname") String nickname);
    Optional<UserReqDto> findById(@Param("id") Long id);
    UserReqDto findByEmail(String email);
    void insertUser(UserReqDto user);
    UserReqDto findByNickname(String nickname);
    boolean updateMyInfo(UserReqDto user);
    boolean deleteMyInfo(CustomUserDetails user);
}
