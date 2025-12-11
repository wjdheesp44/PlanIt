package com.gt.planit.domain.user.model.mapper;

import com.gt.planit.domain.user.model.dto.UserReqDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserReqDto findByEmail(String email);
}
