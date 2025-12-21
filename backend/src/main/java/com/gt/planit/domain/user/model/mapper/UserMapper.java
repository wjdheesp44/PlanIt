package com.gt.planit.domain.user.model.mapper;

import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.security.service.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserReqDto findByEmail(String email);
    void insertUser(UserReqDto user);
    UserReqDto findByNickname(String nickname);
    boolean updateMyInfo(UserReqDto user);
    boolean deleteMyInfo(CustomUserDetails user);
}
