package com.bringup.member.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVInsertRequestDto {
    private boolean mainCv;
    private String skill;
    private int userIndex;
    private String title;
}
