package com.gt.planit.domain.user.controller;

import com.gt.planit.domain.plan.model.dto.GroupUserResDto;
import com.gt.planit.domain.plan.model.dto.SearchUserResDto;
import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.service.UserService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> signup(@RequestBody UserReqDto dto) {
        userService.signup(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean exists = userService.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }

    /**
     * 유저 닉네임 검색
     */
    @GetMapping("/v1/users")
    public ResponseEntity<List<SearchUserResDto>> getGroupUsers(
            @RequestParam String nickname,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        List<SearchUserResDto> users = userService.getByNickname(nickname);
        return ResponseEntity.ok(users);
    }

}
