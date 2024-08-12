package com.bringup.member.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String userEmail;
    private String userPassword;
}