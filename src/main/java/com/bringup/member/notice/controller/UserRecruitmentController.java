package com.bringup.member.notice.controller;

import com.bringup.member.notice.domain.service.UserRecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bringup.company.recruitment.entity.Recruitment;
import java.util.List;

@RestController
@RequestMapping("/recruitment")
public class UserRecruitmentController {

    @Autowired
    private UserRecruitmentService userRecruitmentService;

    @GetMapping
    public List<Recruitment> getAllRecruitments() {
        return userRecruitmentService.getAllRecruitments();
    }

    @GetMapping("/view")
    public String View() {
        return "member/user/recruitmentList";
    }


    @GetMapping("/scrap")
    public ResponseEntity<List<Recruitment>> getBookmarkedRecruitments() {
        List<Recruitment> recruitments = userRecruitmentService.getBookmarkedRecruitments();
        return ResponseEntity.ok(recruitments);
    }
}