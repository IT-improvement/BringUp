package com.bringup.company.recruitment.service;

import com.bringup.common.enums.NotificationType;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.RecruitmentErrorCode.BAD_REQUEST;
import static com.bringup.common.enums.RecruitmentErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;

    // 공고 리스트업
    public List<RecruitmentResponseDto> getRecruitments(UserDetailsImpl userDetails) {
        System.out.println("userDetail in service : " + userDetails.getId());
        List<Recruitment> recruitments = null;

        try {
            recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());
        } catch (Exception e) {
            e.printStackTrace();  // 예외를 출력하여 무슨 오류가 발생하는지 확인
            System.out.println("Exception occurred during recruitment fetch");
        }

        if (recruitments == null) {
            System.out.println("Recruitments is null");
        } else if (recruitments.isEmpty()) {
            System.out.println("Recruitments is empty");
        } else {
            System.out.println("Number of recruitments: " + recruitments.size());
        }

        List<RecruitmentResponseDto> recruitmentResponseDtos = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            RecruitmentResponseDto dto = convertToDto(recruitment);
            recruitmentResponseDtos.add(dto);
            System.out.println("추가완료");
        }

        return recruitmentResponseDtos;
    }

    // 공고 작성
    @Transactional
    public void createRecruitment(UserDetailsImpl userDetails, RecruitmentRequestDto requestDto, MultipartFile img) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        Recruitment recruitment = new Recruitment();
        recruitment.setCompany(company);
        recruitment.setRecruitmentTitle(requestDto.getRecruitmentTitle());
        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(requestDto.getStartDate(), formatter);
        LocalDate periodEndDate = calculatePeriod(startDate, requestDto.getPeriod());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(periodEndDate.format(formatter));

        recruitment.setStatus(StatusType.CRT_WAIT);

        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void updateRecruitment(UserDetailsImpl userDetails, Integer recruitmentIndex, RecruitmentRequestDto requestDto, MultipartFile img) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        // 회사 아이디 일치 여부 확인
        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setRecruitmentTitle(requestDto.getRecruitmentTitle());
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());
        recruitment.setStartDate(requestDto.getStartDate());
        recruitment.setPeriod(requestDto.getPeriod());
        recruitment.setStatus(StatusType.ACTIVE);

        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void deleteRecruitment(UserDetailsImpl userDetails, Integer recruitmentIndex, String reason) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        // 회사 아이디 일치 여부 확인
        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        recruitment.setStatus(StatusType.DEL_WAIT);
        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public RecruitmentResponseDto getRecruitmentDetail(UserDetailsImpl userDetails, Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        return convertToDto(recruitment);
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
            recruitment.setStatus(StatusType.INACTIVE);
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