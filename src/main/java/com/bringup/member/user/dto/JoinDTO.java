package com.bringup.member.user.dto;

import com.bringup.common.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String gender;
    private List<MilitaryItem> militaryList;
}
