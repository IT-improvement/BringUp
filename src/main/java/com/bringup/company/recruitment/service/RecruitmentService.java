package com.bringup.company.recruitment.service;

import com.bringup.common.enums.*;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.*;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.recruitment.repository.RecruitmentFreelancerRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.RecruitmentErrorCode.*;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;
    private final RecruitmentFreelancerRepository recruitmentFreelancerRepository;
    private final ApplyRecruitmentRepository applyCvRepository;

    // 공고 리스트업
    public List<RecruitmentResponseDto> getRecruitments(UserDetailsImpl userDetails) {
        // 공고를 가져옴
        List<Recruitment> recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());

        // 공고가 없을 경우 예외를 던짐
        if (recruitments == null || recruitments.isEmpty()) {
            throw new RecruitmentException(NOT_FOUND_RECRUITMENT); // 적절한 에러 코드 사용
        }

        List<RecruitmentResponseDto> recruitmentResponseDtos = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            RecruitmentResponseDto dto = convertToDto(recruitment); // DTO로 변환
            recruitmentResponseDtos.add(dto); // 리스트에 추가
        }

        return recruitmentResponseDtos; // 변환된 DTO 리스트 반환
    }

    // 통합 공고 리스트 가져오기 (일반 공고 + 프리랜서 프로젝트)
    @Transactional
    public List<UnifiedRecruitmentDto> getAllRecruitments(UserDetailsImpl userDetails) {
        List<UnifiedRecruitmentDto> unifiedRecruitments = new ArrayList<>();

        // 1. 일반 공고 리스트 가져오기
        List<Recruitment> recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());
        for (Recruitment recruitment : recruitments) {
            // 지원자 수 계산
            int applyCount = applyCvRepository.countByRecruitmentIndexAndApplicationType(recruitment.getRecruitmentIndex(), ApplicationType.RECRUITMENT);

            UnifiedRecruitmentDto dto = UnifiedRecruitmentDto.builder()
                    .index(recruitment.getRecruitmentIndex())
                    .title(recruitment.getRecruitmentTitle())
                    .type(ApplicationType.RECRUITMENT)  // 일반 공고 타입
                    .recruitmentType(recruitment.getRecruitmentType()) // 정규직/비정규직
                    .period(recruitment.getPeriod())  // 채용 종료일
                    .viewCount(recruitment.getViewCount())  // 조회수
                    .applicantCount(applyCount)  // 지원자 수
                    .build();
            unifiedRecruitments.add(dto);
        }

        // 2. 프리랜서 프로젝트 리스트 가져오기
        List<RecruitmentFreelancer> freelancerProjects = recruitmentFreelancerRepository.findAllByCompanyCompanyId(userDetails.getId());
        for (RecruitmentFreelancer project : freelancerProjects) {
            // 지원자 수 계산
            int applyCount = applyCvRepository.countByRecruitmentIndexAndApplicationType(project.getProjectIndex().intValue(), ApplicationType.FREELANCER);

            UnifiedRecruitmentDto dto = UnifiedRecruitmentDto.builder()
                    .index(project.getProjectIndex())
                    .title(project.getProjectTitle())
                    .type(ApplicationType.FREELANCER)  // 프리랜서 공고 타입
                    .recruitmentType(RecruitmentType.FREE)  // 프리랜서 타입으로 설정
                    .period(project.getPeriod().toString())  // 프로젝트 기간
                    .viewCount(0)  // 프리랜서 프로젝트의 조회수는 0으로 가정
                    .applicantCount(applyCount)  // 지원자 수
                    .build();
            unifiedRecruitments.add(dto);
        }

        return unifiedRecruitments;
    }

    // 공고 작성
    @Transactional
    public void createRecruitment(UserDetailsImpl userDetails, RecruitmentRequestDto requestDto) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        Recruitment recruitment = new Recruitment();
        recruitment.setCompany(company);
        recruitment.setRecruitmentTitle(requestDto.getRecruitmentTitle());
        recruitment.setRecruitmentType(requestDto.getRecruitmentType());
        recruitment.setWorkDetail(requestDto.getWorkDetail());
        recruitment.setHospitality(requestDto.getHospitality());
        recruitment.setRequirement(requestDto.getRequirement());
        recruitment.setCategory(requestDto.getCategory());
        recruitment.setSkill(requestDto.getSkill());
        recruitment.setCareer(requestDto.getCareer());
        recruitment.setSalary(requestDto.getSalary());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate;
        startDate = LocalDate.now().format(formatter);

        recruitment.setStartDate(startDate);
        recruitment.setPeriod(requestDto.getPeriod());

        recruitment.setStatus(StatusType.CRT_WAIT);

        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void updateRecruitment(UserDetailsImpl userDetails, Integer recruitmentIndex, RecruitmentRequestDto requestDto) {
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
        recruitment.setCareer(requestDto.getCareer());
        recruitment.setSalary(requestDto.getSalary());
        recruitment.setWorkDetail(requestDto.getWorkDetail());
        recruitment.setHospitality(requestDto.getHospitality());
        recruitment.setRequirement(requestDto.getRequirement());
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
    public RecruitmentDetailResponseDto getRecruitmentDetail(UserDetailsImpl userDetails, Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_MEMBER_ID));

        String[] images = company.getCompanyImg().replaceAll(" ", "").replaceAll("\n", "").split(",");

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        return convertToDto(recruitment, images);
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

    private RecruitmentDetailResponseDto convertToDto(Recruitment recruitment, String[] image) {
        return RecruitmentDetailResponseDto.builder()
                .c_name(recruitment.getCompany().getCompanyName())
                .c_address(recruitment.getCompany().getCompanyAddress())
                .c_img(image)
                .c_intro(recruitment.getCompany().getCompanyVision())
                .c_welfare(recruitment.getCompany().getCompanyWelfare())
                .c_logo(recruitment.getCompany().getCompanyLogo())
                .r_career(recruitment.getCareer())
                .r_hospitality(recruitment.getHospitality())
                .r_title(recruitment.getRecruitmentTitle())
                .r_period(recruitment.getPeriod())
                .r_requirement(recruitment.getRequirement())
                .r_salary(recruitment.getSalary())
                .r_workdetail(recruitment.getWorkDetail())
                .build();
        }

    // convertToDto 메서드 추가
    private RecruitmentResponseDto convertToDto(Recruitment recruitment) {
        return RecruitmentResponseDto.builder()
                .recruitmentIndex(recruitment.getRecruitmentIndex())
                .recruitmentTitle(recruitment.getRecruitmentTitle())
                .managerEmail(recruitment.getCompany().getManagerEmail())
                .recruitmentType(recruitment.getRecruitmentType())
                .category(recruitment.getCategory())
                .skill(recruitment.getSkill())
                .workDetail(recruitment.getWorkDetail())
                .companyImg(recruitment.getCompany().getCompanyImg())
                .hospitality(recruitment.getHospitality())
                .requirement(recruitment.getRequirement())
                .period(recruitment.getPeriod())
                .status(recruitment.getStatus())
                .build();
    }


    public List<ApplyCvListResponseDto> getApplyCvs(UserDetailsImpl userDetails) {
        // 회사 정보 가져오기
        Company company = companyRepository.findBycompanyId(userDetails.getId())
                .orElseThrow(() -> new CompanyException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        // 회사의 모든 공고 목록 조회
        List<Recruitment> recruitmentList = recruitmentRepository.findAllByCompanyCompanyId(company.getCompanyId());

        // 결과 리스트
        List<ApplyCvListResponseDto> applyCvList = new ArrayList<>();

        // 각 공고에 대해 지원 이력 조회 및 매핑
        for (Recruitment recruitment : recruitmentList) {
            // 지원 이력 조회
            List<ApplyRecruitmentEntity> applyEntities = applyCvRepository.findByRecruitmentIndex(recruitment.getRecruitmentIndex());

            // ApplyRecruitmentEntity를 ApplyCvListResponseDto로 매핑하여 결과 리스트에 추가
            for (ApplyRecruitmentEntity applyEntity : applyEntities) {
                ApplyCvListResponseDto dto = ApplyCvListResponseDto.builder()
                        .applyCvIdx(applyEntity.getApplyCVIndex())
                        .cvIdx(applyEntity.getCvIndex())
                        .type(applyEntity.getApplicationType().name())
                        .status(applyEntity.getStatus().name())
                        .applyDate(applyEntity.getApplyCVDate().toString())
                        .build();

                applyCvList.add(dto);
            }
        }

        return applyCvList;
    }

}