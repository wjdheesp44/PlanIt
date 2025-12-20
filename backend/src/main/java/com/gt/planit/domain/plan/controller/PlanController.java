package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.user.model.service.UserService;
import com.gt.planit.security.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {
    private final UserService userService;

    @PostMapping("/v1/plans")
    public ResponseEntity<UserResDto> me(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResDto user = userService.getByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
}
