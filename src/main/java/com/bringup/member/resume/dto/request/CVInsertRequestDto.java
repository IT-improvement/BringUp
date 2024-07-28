package com.bringup.member.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVInsertRequestDto {
    private String cvimage;
    private boolean maincv;
    private String education;
    private String skill;
    private String useremail;
}
