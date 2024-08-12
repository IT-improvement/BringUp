package com.bringup.member.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String userEmail;
    private String userPassword;
}