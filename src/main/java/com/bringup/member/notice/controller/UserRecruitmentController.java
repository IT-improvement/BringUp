package com.bringup.member.notice.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.notice.domain.service.UserRecruitmentService;
import com.bringup.member.notice.dto.response.UserRecruitmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<UserRecruitmentDto> getAllRecruitments() {
        return userRecruitmentService.getAllRecruitments();
    }
    @GetMapping("/view")
    public ResponseEntity<?> getRecruitmentPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 현재 로그인된 사용자 정보 확인
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        // 사용자 정보로 작업 수행
        return ResponseEntity.ok("member/user/recruitmentList"); // 실제 페이지 반환 또는 view 리턴
    }


    @GetMapping("/scrap")
    public ResponseEntity<List<UserRecruitmentDto>> getBookmarkedRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getBookmarkedRecruitments();
        return ResponseEntity.ok(recruitments);
    }
}