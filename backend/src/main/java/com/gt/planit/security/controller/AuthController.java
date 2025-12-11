package com.gt.planit.security.controller;

import com.gt.planit.security.jwt.JWTUtil;
import com.gt.planit.domain.user.model.dto.UserReqDto;
import com.gt.planit.domain.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authorization) {
        // "Bearer <token>" → 토큰만 추출
        String refreshToken = authorization.replace("Bearer ", "");

        // 1) refresh token 검증
        var claims = jwtUtil.getClaims(refreshToken);

        // 2) refresh token 인지 확인
        if (!claims.getSubject().equals("refresh")) {
            return ResponseEntity.status(401).body("Refresh Token 이 아님");
        }

        // 3) 사용자 정보 로드
        String email = claims.get("username", String.class);

        UserReqDto user = userMapper.findByEmail(email);
        if (user == null) {
            log.info("유저 없음: {}",email);
            throw new UsernameNotFoundException("유저 없음: " + email);
        }

        // 4) 새로운 access token 발급
        String newAccessToken = jwtUtil.createAccessToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok().body(
                java.util.Map.of("accessToken", newAccessToken)
        );
    }
}
