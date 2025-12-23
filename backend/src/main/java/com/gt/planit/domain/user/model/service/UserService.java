package com.gt.planit.domain.user.model.service;

import com.gt.planit.domain.plan.model.dto.GroupShareReqDto;
import com.gt.planit.domain.plan.model.dto.GroupUserResDto;
import com.gt.planit.domain.plan.model.dto.SearchUserResDto;
import com.gt.planit.domain.plan.model.entity.GroupRole;
import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.mapper.UserMapper;
import com.gt.planit.security.dto.MyUserResDto;
import com.gt.planit.security.dto.UserResDto;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(UserReqDto dto) {

        if (userMapper.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        UserReqDto user = new UserReqDto();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setRole("USER");

        userMapper.insertUser(user);
    }

    public boolean existsByEmail(String email) {
        return userMapper.findByEmail(email) != null;
    }

    public boolean existsByNickname(String nickname) {
        return userMapper.findByNickname(nickname) != null;
    }

    public UserResDto getByEmail(String email) {
        UserReqDto user = userMapper.findByEmail(email);

        return new UserResDto(
                user.getEmail(),
                user.getNickname(),
                user.getName(),
                user.getRole()
        );
    }

    public List<SearchUserResDto> getByNickname(String nickname) {
        List<SearchUserResDto> dtos = userMapper.searchUsers(nickname);
        return dtos;
    }

    public boolean updateMyInfo(UserReqDto dto) {
        UserReqDto user = new UserReqDto();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());

        return userMapper.updateMyInfo(user);
    }

    public boolean deleteMyInfo(CustomUserDetails dto) {
        return userMapper.deleteMyInfo(dto);
    }

}
