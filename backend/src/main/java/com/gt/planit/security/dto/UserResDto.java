package com.gt.planit.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public class UserResDto {
    private String email;
    private String nickname;
    private String name;
    private String role;
}
