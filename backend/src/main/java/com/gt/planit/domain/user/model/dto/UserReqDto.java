package com.gt.planit.domain.user.model.dto;

import lombok.Data;

@Data
public class UserReqDto {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String role;
}
