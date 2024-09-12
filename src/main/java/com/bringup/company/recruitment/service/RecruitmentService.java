package com.bringup.company.recruitment.service;

import com.bringup.common.enums.NotificationType;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.RecruitmentDetailResponseDto;
import com.bringup.company.recruitment.dto.response.RecruitmentMainResponseDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.RecruitmentErrorCode.*;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;

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

    public List<RecruitmentMainResponseDto> getRecruitmentsinMain(UserDetailsImpl userDetails) {
        // 공고를 가져옴
        List<Recruitment> recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());

        // 공고가 없을 경우 예외를 던짐
        if (recruitments == null || recruitments.isEmpty()) {
            throw new RecruitmentException(NOT_FOUND_RECRUITMENT); // 적절한 에러 코드 사용
        }

        List<RecruitmentMainResponseDto> RecruitmentMainResponseDtos = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            RecruitmentMainResponseDto dto = RecruitmentMainResponseDto.builder()
                    .r_index(recruitment.getRecruitmentIndex())
                    .r_title(recruitment.getRecruitmentTitle())
                    .r_career(recruitment.getCareer())
                    .r_period(recruitment.getPeriod())
                    .r_skill(recruitment.getSkill())
                    .r_category(recruitment.getCategory())
                    .build();

            RecruitmentMainResponseDtos.add(dto);
        }
        return RecruitmentMainResponseDtos;
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


}