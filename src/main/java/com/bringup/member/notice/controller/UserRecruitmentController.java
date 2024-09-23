package com.bringup.member.notice.controller;

import com.bringup.member.notice.domain.service.UserRecruitmentService;
import com.bringup.member.notice.dto.response.UserRecruitmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recruitment")
public class UserRecruitmentController {

    @Autowired
    private UserRecruitmentService userRecruitmentService;





    @GetMapping("/list")
    public ResponseEntity<List<UserRecruitmentDto>> getAllRecruitments() {

        List<UserRecruitmentDto> recruitments = userRecruitmentService.getAllRecruitments();
        return ResponseEntity.ok(recruitments); // JSON 형식으로 반환
    }


    @GetMapping("/scrap")
    public ResponseEntity<List<UserRecruitmentDto>> getBookmarkedRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getBookmarkedRecruitments();
        return ResponseEntity.ok(recruitments);
    }
}