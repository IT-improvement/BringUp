package com.bringup.member.membership.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMembershipDto {

    private int userIndex;
    private String startDate;
    private String period;
}