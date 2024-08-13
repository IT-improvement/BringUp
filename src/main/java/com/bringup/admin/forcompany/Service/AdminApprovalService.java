package com.bringup.admin.forcompany.Service;

import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminApprovalService {

    private final RecruitmentRepository recruitmentRepository;

    @RabbitListener(queues = "approvalQueue")
    public void handleApprovalRequest(Recruitment recruitment) {
        // 수신된 승인 요청을 처리하는 로직
        System.out.println("Received approval request for Recruitment ID: " + recruitment.getRecruitmentIndex());

        // 어드민이 승인한 경우 상태를 "승인"으로 설정
        recruitment.setStatus("승인");
        recruitmentRepository.save(recruitment);
    }

    @RabbitListener(queues = "approvalQueue")
    public void handleDeletionRequest(Recruitment recruitment) {
        // 수신된 삭제 승인 요청을 처리하는 로직
        System.out.println("Received deletion request for Recruitment ID: " + recruitment.getRecruitmentIndex());

        // 어드민이 삭제를 승인한 경우 상태를 "삭제"로 설정
        recruitment.setStatus("삭제");
        recruitmentRepository.save(recruitment);
    }

    public void approveRecruitment(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));
        recruitment.setStatus("승인");
        recruitmentRepository.save(recruitment);
    }

    public void rejectRecruitment(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));
        recruitment.setStatus("거절");
        recruitmentRepository.save(recruitment);
    }

    public void approveDeletion(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        recruitment.setStatus("삭제");
        recruitmentRepository.save(recruitment);
    }
}