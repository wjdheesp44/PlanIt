package com.gt.planit.domain.user.controller;

import com.gt.planit.security.dto.MyUserResDto;
import com.gt.planit.security.dto.UserResDto;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    @GetMapping("/v1/profile")
    public MyUserResDto getMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        return new MyUserResDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUsername(),
                user.getRole()
        );
    }

}
