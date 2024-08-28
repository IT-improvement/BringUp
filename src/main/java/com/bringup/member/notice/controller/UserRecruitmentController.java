package com.bringup.member.notice.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.notice.domain.service.UserRecruitmentService;
import com.bringup.member.notice.dto.response.UserRecruitmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bringup.company.recruitment.entity.Recruitment;
import java.util.List;

@Controller
@RequestMapping("/recruitment")
public class UserRecruitmentController {

    @Autowired
    private UserRecruitmentService userRecruitmentService;


    @GetMapping("/list")
    public String showRecruitmentListPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 인증된 사용자만 이 페이지에 접근할 수 있음
        if (userDetails == null) {
            return "redirect:/member/userLoginForm"; // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        }

        // 인증된 사용자라면 recruitmentList.jsp 페이지를 렌더링
        return "member/user/potofolio";
    }

    @GetMapping
    public List<UserRecruitmentDto> getAllRecruitments() {
        return userRecruitmentService.getAllRecruitments();
    }


    @GetMapping("/auth")
    public ResponseEntity<List<UserRecruitmentDto>> getAllRecruitments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // 인증되지 않은 경우
        }

        List<UserRecruitmentDto> recruitments = userRecruitmentService.getAllRecruitments();
        return ResponseEntity.ok(recruitments); // JSON 형식으로 반환
    }


    @GetMapping("/scrap")
    public ResponseEntity<List<UserRecruitmentDto>> getBookmarkedRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getBookmarkedRecruitments();
        return ResponseEntity.ok(recruitments);
    }
}