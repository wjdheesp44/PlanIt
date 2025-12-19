package com.gt.planit.security.service;

import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserReqDto user = userMapper.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("유저 없음: " + email);
        }

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getRole()
        );
    }
}
