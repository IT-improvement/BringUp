package com.bringup.member.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVInsertRequestDto {
    private int cvIndex;
    private String cvimage;
    private boolean maincv;
    private String education;
    private String skill;
    private int userIndex;
    private String status;
    private String title;
}
