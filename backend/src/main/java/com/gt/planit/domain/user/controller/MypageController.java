package com.gt.planit.domain.user.controller;

import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.service.UserService;
import com.gt.planit.security.dto.MyUserResDto;
import com.gt.planit.security.dto.UserResDto;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final UserService userService;

    @GetMapping("/v1/profile")
    public MyUserResDto getMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        return new MyUserResDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getName(),
                user.getRole()
        );
    }

    @PutMapping("/v1/profile")
    public ResponseEntity<Boolean> updateMyInfo(@RequestBody UserReqDto user) {
        boolean exists = userService.updateMyInfo(user);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/v1/profile")
    public ResponseEntity<Boolean> deleteMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        boolean exists = userService.deleteMyInfo(user);
        return ResponseEntity.ok(exists);
    }

}
