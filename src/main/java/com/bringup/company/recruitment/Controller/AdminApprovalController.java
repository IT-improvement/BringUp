package com.bringup.company.recruitment.Controller;

import com.bringup.common.response.BfResponse;
import com.bringup.company.recruitment.Service.AdminApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/recruitment")
public class AdminApprovalController {
    private final AdminApprovalService adminApprovalService;

    @PostMapping("/approve/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> approveRecruitment(@PathVariable Integer recruitmentId) {
        adminApprovalService.approveRecruitment(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment approved successfully"));
    }

    @PostMapping("/reject/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> rejectRecruitment(@PathVariable Integer recruitmentId) {
        adminApprovalService.rejectRecruitment(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment rejected successfully"));
    }
}