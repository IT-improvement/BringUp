package com.bringup.admin.forcompany.Controller;

import com.bringup.common.response.BfResponse;
import com.bringup.admin.forcompany.Service.AdminApprovalService;
import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 승인"));
    }

    @PostMapping("/reject/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> rejectRecruitment(@PathVariable Integer recruitmentId) {
        adminApprovalService.rejectRecruitment(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 거절"));
    }

    @PostMapping("/delete/approve/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> approveRecruitmentDeletion(@PathVariable Integer recruitmentId) {
        adminApprovalService.approveDeletion(recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "공고 삭제"));
    }
}