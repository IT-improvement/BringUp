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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;

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
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(requestDto.getPeriod());
        recruitment.setStatus("생성 대기");
        recruitment.setRecruitmentClass(requestDto.getRecruitmentClass());

        recruitmentRepository.save(recruitment);

        // 어드민에게 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        notifyAdminForApproval(recruitment);
    }

    private void notifyAdminForApproval(Recruitment recruitment) {
        // 어드민에게 승인 요청을 보내는 로직을 작성합니다.
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

        // 어드민에게 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        notifyAdminForApproval(recruitment);
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

        // 어드민에게 삭제 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        notifyAdminForDeletionApproval(recruitment, reason);
    }

    private void notifyAdminForDeletionApproval(Recruitment recruitment, String reason) {
        // 어드민에게 삭제 승인 요청을 보내는 로직을 작성합니다.
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

    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 어드민 완성시 사용할 로직 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    /*
    @Transactional
    public void updateRecruitment(CompanyDetailsImpl userDetails, Integer recruitmentIndex, RecruitmentRequestDto requestDto) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(BAD_REQUEST);
        }

        // 어드민에게 수정 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        sendUpdateRequestToAdmin(userDetails, recruitment, requestDto);
    }

    @Transactional
    public void deleteRecruitment(CompanyDetailsImpl userDetails, Integer recruitmentIndex, String reason) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(BAD_REQUEST);
        }

        // 어드민에게 삭제 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        sendDeleteRequestToAdmin(userDetails, recruitment, reason);
    }

    private void sendUpdateRequestToAdmin(CompanyDetailsImpl userDetails, Recruitment recruitment, RecruitmentRequestDto requestDto) {
        // 어드민에게 수정 요청을 보내는 로직을 작성합니다.
        // 예시로 HTTP 클라이언트를 사용하여 어드민에게 요청을 보낼 수 있습니다.

        // JSON 형태로 전송할 데이터를 구성합니다.
        String jsonData = constructUpdateJson(userDetails, recruitment, requestDto);

        // 실제 HTTP 요청을 통해 어드민에게 데이터를 전송합니다.
        sendHttpRequestToAdmin(jsonData);
    }

    private void sendDeleteRequestToAdmin(CompanyDetailsImpl userDetails, Recruitment recruitment, String reason) {
        // 어드민에게 삭제 요청을 보내는 로직을 작성합니다.
        // 예시로 HTTP 클라이언트를 사용하여 어드민에게 요청을 보낼 수 있습니다.

        // JSON 형태로 전송할 데이터를 구성합니다.
        String jsonData = constructDeleteJson(userDetails, recruitment, reason);

        // 실제 HTTP 요청을 통해 어드민에게 데이터를 전송합니다.
        sendHttpRequestToAdmin(jsonData);
    }

    private String constructUpdateJson(CompanyDetailsImpl userDetails, Recruitment recruitment, RecruitmentRequestDto requestDto) {
        // JSON 데이터를 생성하는 로직을 작성합니다.
        return "{ \"action\": \"update\", \"recruitmentId\": " + recruitment.getRecruitmentIndex() + ", \"companyId\": " + recruitment.getCompany().getCompanyId() +
                ", \"request\": " + requestDto.toString() + ", \"userId\": " + userDetails.getId() + " }";
    }

    private String constructDeleteJson(CompanyDetailsImpl userDetails, Recruitment recruitment, String reason) {
        // JSON 데이터를 생성하는 로직을 작성합니다.
        return "{ \"action\": \"delete\", \"recruitmentId\": " + recruitment.getRecruitmentIndex() + ", \"companyId\": " + recruitment.getCompany().getCompanyId() +
                ", \"reason\": \"" + reason + "\", \"userId\": " + userDetails.getId() + " }";
    }

    private void sendHttpRequestToAdmin(String jsonData) {
        // 실제 HTTP 요청을 통해 어드민에게 데이터를 전송하는 로직을 작성합니다.
        // 예: HttpClient, RestTemplate, WebClient 등을 사용하여 HTTP 요청을 보낼 수 있습니다.
    }
    */
}