package com.bringup.member.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MilitaryItem {
    private String militaryStatus;
    private String militaryType;
    private String specialty;
    private String rankName;
    private String dischargeReason;
    private Date enlistmentDate;
    private Date dischargeDate;
    private String exemptionReason;
}
