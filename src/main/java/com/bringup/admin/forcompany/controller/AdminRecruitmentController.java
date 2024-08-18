package com.bringup.admin.forcompany.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.admin.forcompany.service.AdminRecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/admin/recruitment")
@RequiredArgsConstructor
public class AdminRecruitmentController {

    private final AdminRecruitmentService adminRecruitmentService;

    @PostMapping("/approve/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> approveRecruitment(@PathVariable Integer recruitmentId) {
        adminRecruitmentService.approveRecruitment(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 승인"));
    }

    @PostMapping("/reject/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> rejectRecruitment(@PathVariable Integer recruitmentId) {
        adminRecruitmentService.rejectRecruitment(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 거절"));
    }

    @PostMapping("/delete/approve/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> approveRecruitmentDeletion(@PathVariable Integer recruitmentId) {
        adminRecruitmentService.approveDeletion(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 삭제"));
    }
}