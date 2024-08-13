package com.bringup.company.recruitment.Service;

import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.user.Entity.Company;
import com.bringup.company.user.Repository.CompanyRepository;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.recruitment.DTO.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.DTO.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationService notificationService;

    public List<RecruitmentResponseDto> getRecruitments(CompanyDetailsImpl userDetails) {
        return recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createRecruitment(CompanyDetailsImpl userDetails, RecruitmentRequestDto requestDto) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Recruitment recruitment = new Recruitment();
        recruitment.setCompany(company);
        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(requestDto.getStartDate(), formatter);
        LocalDate periodEndDate = calculatePeriod(startDate, requestDto.getPeriod());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(periodEndDate.format(formatter));

        recruitment.setStatus("생성 대기");
        recruitment.setRecruitmentClass(requestDto.getRecruitmentClass());

        recruitmentRepository.save(recruitment);

        // 메시지를 RabbitMQ로 전송
        sendApprovalRequestToAdmin(recruitment, "create");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                "ROLE_COMPANY",
                NotificationType.RECRUITMENT_APPROVAL.name(),
                "Your recruitment request has been created and is awaiting approval."
        );
    }

    @Transactional
    public void updateRecruitment(CompanyDetailsImpl userDetails, Integer recruitmentIndex, RecruitmentRequestDto requestDto) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(BAD_REQUEST);
        }

        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(requestDto.getPeriod());
        recruitment.setStatus("수정 대기");
        recruitment.setRecruitmentClass(requestDto.getRecruitmentClass());

        recruitmentRepository.save(recruitment);

        // 어드민에게 승인 요청을 보냅니다.
        sendApprovalRequestToAdmin(recruitment, "update");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                "ROLE_COMPANY",
                NotificationType.RECRUITMENT_REJECTION.name(),
                "Your recruitment request has been updated and is awaiting approval."
        );
    }

    @Transactional
    public void deleteRecruitment(CompanyDetailsImpl userDetails, Integer recruitmentIndex, String reason) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(BAD_REQUEST);
        }

        recruitment.setStatus("삭제 대기");
        recruitmentRepository.save(recruitment);

        // 어드민에게 삭제 승인 요청을 보냅니다.
        sendApprovalRequestToAdmin(recruitment, "delete");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                "ROLE_COMPANY",
                NotificationType.RECRUITMENT_REJECTION.name(),
                "Your recruitment request has been deleted."
        );
    }

    private void sendApprovalRequestToAdmin(Recruitment recruitment, String actionType) {
        String exchange = "approvalExchange";
        String routingKey = "approval." + actionType;
        rabbitTemplate.convertAndSend(exchange, routingKey, recruitment);
    }

    private LocalDate calculatePeriod(LocalDate startDate, String periodDuration) {
        int durationInMonths = Integer.parseInt(periodDuration.replace("months", "").trim());
        return startDate.plusMonths(durationInMonths);
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void updateRecruitmentStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);

        // 오늘 날짜와 일치하는 Period를 가진 Recruitment 조회
        List<Recruitment> recruitments = recruitmentRepository.findAllByPeriod(today);

        for (Recruitment recruitment : recruitments) {
            recruitment.setStatus("삭제");
            recruitmentRepository.save(recruitment);
        }

        // "삭제 대기" 상태를 "삭제" 상태로 변경
        List<Recruitment> pendingDeletions = recruitmentRepository.findAllByStatus("삭제 대기");

        for (Recruitment recruitment : pendingDeletions) {
            recruitment.setStatus("삭제");
            recruitmentRepository.save(recruitment);
        }
    }

    private RecruitmentResponseDto convertToDto(Recruitment recruitment) {
        return RecruitmentResponseDto.builder()
                .recruitmentIndex(recruitment.getRecruitmentIndex())
                .managerEmail(recruitment.getCompany().getManagerEmail())
                .recruitmentType(recruitment.getRecruitmentType())
                .category(recruitment.getCategory())
                .skill(recruitment.getSkill())
                .startDate(recruitment.getStartDate())
                .period(recruitment.getPeriod())
                .status(recruitment.getStatus())
                .recruitmentClass(recruitment.getRecruitmentClass())
                .build();
    }
}