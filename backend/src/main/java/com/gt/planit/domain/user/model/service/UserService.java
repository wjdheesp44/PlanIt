package com.gt.planit.domain.user.model.service;

import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.mapper.UserMapper;
import com.gt.planit.security.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
