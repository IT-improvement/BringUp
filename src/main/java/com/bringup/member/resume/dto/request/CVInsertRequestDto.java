package com.bringup.member.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CVInsertRequestDto {
    private boolean mainCv;
    private List<String> skill;
    private String title;
    private List<Integer> cvAward;
    private List<Integer> cvBlog;
    private List<Integer> cvCareer;
    private List<Integer> cvCertificate;
    private List<Integer> cvSchool;
    private List<String> github;
}
