package com.gt.planit.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyUserResDto {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String role;
}
