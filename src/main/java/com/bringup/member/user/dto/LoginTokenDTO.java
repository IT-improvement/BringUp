package com.bringup.member.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginTokenDTO {
    private String accessToken;

}
