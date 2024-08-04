package com.bringup.company.recruitment.Service;

import com.bringup.common.jwt.JWTUtil;
import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Repository.CompanyRepository;
import com.bringup.company.recruitment.DTO.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.DTO.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;
    private final JWTUtil jwtUtil;

    public List<RecruitmentResponseDto> getRecruitments(Long companyId) {
        return recruitmentRepository.findAllByCompanyCompanyId(companyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createRecruitment(String token, RecruitmentRequestDto requestDto) {
        String username = jwtUtil.getUsername(token);
        Company company = companyRepository.findByManagerEmail(username)
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
    public void updateRecruitment(String token, Integer recruitmentIndex, RecruitmentRequestDto requestDto) {
        String username = jwtUtil.getUsername(token);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

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
    public void deleteRecruitment(String token, Integer recruitmentIndex, String reason) {
        String username = jwtUtil.getUsername(token);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

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
}