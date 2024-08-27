package com.bringup.member.membership.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMembershipDto {

    private Long userIndex;
    private String startDate;
    private String period;
}