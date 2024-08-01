package com.bringup.member.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userAddress;
    private String userPhonenumber;
    private String userBirthday;
    private boolean freelancer;
    private String status;
}
