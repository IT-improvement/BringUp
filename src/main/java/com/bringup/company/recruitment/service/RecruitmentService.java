package com.bringup.company.recruitment.service;

import com.bringup.admin.notify.service.NotificationService;
import com.bringup.common.enums.NotificationType;
import com.bringup.common.enums.RolesType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.BAD_REQUEST;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationService notificationService;

    public List<RecruitmentResponseDto> getRecruitments(UserDetailsImpl userDetails) {
        return recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createRecruitment(UserDetailsImpl userDetails, RecruitmentRequestDto requestDto, MultipartFile img) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Recruitment recruitment = new Recruitment();
        recruitment.setCompany(company);
        recruitment.setRecruitmentTitle(requestDto.getRecruitmentTitle());
        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
     /*   recruitment.setRecruitmentImg(saveRecruitmentImage(img));*/
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(requestDto.getStartDate(), formatter);
        LocalDate periodEndDate = calculatePeriod(startDate, requestDto.getPeriod());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(periodEndDate.format(formatter));

        recruitment.setStatus("생성");
//        recruitment.setRecruitmentClass(requestDto.getRecruitmentClass());

        recruitmentRepository.save(recruitment);

        /*// 메시지를 RabbitMQ로 전송
        sendApprovalRequestToAdmin(recruitment, "create");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_APPROVAL,
                "Your recruitment request has been created and is awaiting approval."
        );*/
    }

    private String saveRecruitmentImage(MultipartFile img) {
        if (img.isEmpty()) {
            return null;
        }

        try {
            // 이미지 저장 경로 지정
            String uploadDir = "src/main/resources/static/recruitment/";
            String fileName = "Recruitment_" + UUID.randomUUID().toString() + "_" + img.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, img.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    @Transactional
    public void updateRecruitment(UserDetailsImpl userDetails, Integer recruitmentIndex, RecruitmentRequestDto requestDto, MultipartFile img) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId()==(userDetails.getId()))) {
            throw new CompanyException(BAD_REQUEST);
        }

        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setRecruitmentTitle(requestDto.getRecruitmentTitle());
/*
        recruitment.setRecruitmentImg(saveRecruitmentImage(img));
*/
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(requestDto.getPeriod());
        recruitment.setStatus("수정");

        recruitmentRepository.save(recruitment);

        /*// 어드민에게 승인 요청을 보냅니다.
        sendApprovalRequestToAdmin(recruitment, "update");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_REJECTION,
                "Your recruitment request has been updated and is awaiting approval."
        );*/
    }

    @Transactional
    public void deleteRecruitment(UserDetailsImpl userDetails, Integer recruitmentIndex, String reason) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        if ((!(recruitment.getCompany().getCompanyId()==(userDetails.getId())))) {
            throw new CompanyException(BAD_REQUEST);
        }

        recruitment.setStatus("삭제");
        recruitmentRepository.save(recruitment);

        /*// 어드민에게 삭제 승인 요청을 보냅니다.
        sendApprovalRequestToAdmin(recruitment, "delete");

        // 알림 생성
        notificationService.createNotification(
                userDetails.getId(),
                RolesType.ROLE_COMPANY,
                NotificationType.RECRUITMENT_REJECTION,
                "Your recruitment request has been deleted."
        );*/
    }

    @Transactional
    public RecruitmentResponseDto getRecruitmentDetail(UserDetailsImpl userDetails, Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        if (!(Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId()))) {
            throw new CompanyException(BAD_REQUEST);
        }

        return convertToDto(recruitment);
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
                .recruitmentTitle(recruitment.getRecruitmentTitle())
                .recruitmentType(recruitment.getRecruitmentType())
                .category(recruitment.getCategory())
                .skill(recruitment.getSkill())
                .startDate(recruitment.getStartDate())
                .period(recruitment.getPeriod())
                .status(recruitment.getStatus())
                .build();
    }
}