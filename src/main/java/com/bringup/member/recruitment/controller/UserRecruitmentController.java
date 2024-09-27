package com.bringup.member.recruitment.controller;

import com.bringup.member.recruitment.domain.service.UserRecruitmentService;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recruitment")
@RequiredArgsConstructor
public class UserRecruitmentController {


    private final UserRecruitmentService userRecruitmentService;


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