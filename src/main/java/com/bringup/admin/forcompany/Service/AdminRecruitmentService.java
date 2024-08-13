package com.bringup.admin.forcompany.Service;


import com.bringup.admin.forcompany.exception.AdminException;
import com.bringup.admin.notify.Service.NotificationService;
import com.bringup.common.enums.AdminErrorCode;
import com.bringup.common.enums.NotificationType;
import com.bringup.common.enums.RolesType;
import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final NotificationService notificationService;

    @RabbitListener(queues = "approvalQueue")
    public void handleApprovalRequest(Recruitment recruitment) {
        // RabbitMQ 큐에서 승인 요청 메시지를 수신
        System.out.println("Received approval request for Recruitment ID: " + recruitment.getRecruitmentIndex());

        // 수신된 공고의 상태를 "승인 중"으로 업데이트
        recruitment.setStatus("검토 중");
        recruitmentRepository.save(recruitment);

        // 사용자에게 알림 전송
        notificationService.createNotification(
                recruitment.getCompany().getCompanyId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_APPROVAL,
                "현재 관리자가 당신의 요청을 검토중입니다."
        );
    }

    public void approveRecruitment(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.NOT_FOUND_RECRUITMENT));

        recruitment.setStatus("승인");
        recruitmentRepository.save(recruitment);

        // 사용자에게 승인 알림 전송
        notificationService.createNotification(
                recruitment.getCompany().getCompanyId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_APPROVAL,
                "당사의 공고가 성공적으로 업로드됐습니다."
        );
    }

    public void rejectRecruitment(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.NOT_FOUND_RECRUITMENT));

        recruitment.setStatus("거부");
        recruitmentRepository.save(recruitment);

        // 사용자에게 거절 알림 전송
        notificationService.createNotification(
                recruitment.getCompany().getCompanyId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_REJECTION,
                "당사의 공고가 반려되었습니다. 다시 신청해주세요."
        );
    }

    public void approveDeletion(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.NOT_FOUND_RECRUITMENT));

        recruitment.setStatus("삭제");
        recruitmentRepository.save(recruitment);

        // 사용자에게 삭제 알림 전송
        notificationService.createNotification(
                recruitment.getCompany().getCompanyId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_DELETION,
                "당사의 공고가 성공적으로 삭제됐습니다. 이용해주셔서 감사합니다."
        );
    }
}