package com.bringup.member.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MemberInfoDto {
    private String userName;
    private String userEmail;
}
