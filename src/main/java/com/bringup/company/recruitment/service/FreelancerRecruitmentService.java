package com.bringup.company.recruitment.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.FreelancerProjectRequestDto;
import com.bringup.company.recruitment.dto.response.FreelancerProjectDetailResponseDto;
import com.bringup.company.recruitment.dto.response.FreelancerProjectResponseDto;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.recruitment.repository.RecruitmentFreelancerRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bringup.common.enums.RecruitmentErrorCode.*;

@Service
@RequiredArgsConstructor
public class FreelancerRecruitmentService {
    private final RecruitmentFreelancerRepository recruitmentFreelancerRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void createFreelancerProject(UserDetailsImpl userDetails, FreelancerProjectRequestDto requestDto) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        RecruitmentFreelancer project = new RecruitmentFreelancer();
        project.setCompany(company);
        project.setProjectTitle(requestDto.getProjectTitle());
        project.setProjectDescription(requestDto.getProjectDescription());
        project.setExpectedDuration(requestDto.getExpectedDuration());
        project.setExpectedCost(requestDto.getExpectedCost());
        project.setRequiredSkills(requestDto.getRequiredSkills());
        project.setPreferredSkills(requestDto.getPreferredSkills());
        project.setWorkConditions(requestDto.getWorkConditions());
        project.setPeriod(requestDto.getPreiod());
        project.setStatus(StatusType.CRT_WAIT);

        recruitmentFreelancerRepository.save(project);
    }

    @Transactional
    public void updateFreelancerProject(UserDetailsImpl userDetails, Integer projectId, FreelancerProjectRequestDto requestDto) {
        RecruitmentFreelancer project = recruitmentFreelancerRepository.findById(projectId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        if (!project.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        project.setProjectTitle(requestDto.getProjectTitle());
        project.setProjectDescription(requestDto.getProjectDescription());
        project.setExpectedDuration(requestDto.getExpectedDuration());
        project.setExpectedCost(requestDto.getExpectedCost());
        project.setRequiredSkills(requestDto.getRequiredSkills());
        project.setPreferredSkills(requestDto.getPreferredSkills());
        project.setWorkConditions(requestDto.getWorkConditions());
        project.setPeriod(requestDto.getPreiod());
        project.setStatus(StatusType.CRT_WAIT);

        recruitmentFreelancerRepository.save(project);
    }

    @Transactional
    public void deleteFreelancerProject(UserDetailsImpl userDetails, Integer projectId) {
        RecruitmentFreelancer project = recruitmentFreelancerRepository.findById(projectId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        if (!project.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RecruitmentException(BAD_REQUEST);
        }

        project.setStatus(StatusType.DEL_WAIT);
        recruitmentFreelancerRepository.save(project);
    }

    @Transactional
    public List<FreelancerProjectDetailResponseDto> getFreelancerProjects(UserDetailsImpl userDetails) {
        List<RecruitmentFreelancer> projects = recruitmentFreelancerRepository.findAllByCompanyCompanyId(userDetails.getId());

        if (projects == null || projects.isEmpty()) {
            throw new RecruitmentException(NOT_FOUND_RECRUITMENT);
        }

        List<FreelancerProjectDetailResponseDto> projectResponseDtos = new ArrayList<>();
        for (RecruitmentFreelancer project : projects) {
            FreelancerProjectDetailResponseDto dto = FreelancerProjectDetailResponseDto.builder()
                    .projectIndex(project.getProjectIndex())
                    .projectTitle(project.getProjectTitle())
                    .projectDescription(project.getProjectDescription())
                    .expectedDuration(project.getExpectedDuration())
                    .expectedCost(project.getExpectedCost())
                    .requiredSkills(project.getRequiredSkills())
                    .preferredSkills(project.getPreferredSkills())
                    .workConditions(project.getWorkConditions())
                    .status(project.getStatus())
                    .create_day(project.getCreatedAt().toLocalDate())
                    .build();
            projectResponseDtos.add(dto);
        }

        return projectResponseDtos;
    }

    @Transactional
    public FreelancerProjectDetailResponseDto getFreelancerProjectDetail(UserDetailsImpl userDetails, Integer projectId) {
        RecruitmentFreelancer project = recruitmentFreelancerRepository.findById(projectId)
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_RECRUITMENT));

        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RecruitmentException(NOT_FOUND_MEMBER_ID));

        String[] images = company.getCompanyImg().replaceAll(" ", "").replaceAll("\n", "").split(",");

        return FreelancerProjectDetailResponseDto.builder()
                .c_logo(company.getCompanyLogo())
                .c_img(images)
                .c_name(company.getCompanyName())
                .c_address(company.getCompanyAddress())
                .projectTitle(project.getProjectTitle())
                .projectDescription(project.getProjectDescription())
                .expectedDuration(project.getExpectedDuration())
                .expectedCost(project.getExpectedCost())
                .requiredSkills(project.getRequiredSkills())
                .preferredSkills(project.getPreferredSkills())
                .workConditions(project.getWorkConditions())
                .create_day(project.getCreatedAt().toLocalDate())
                .build();
    }
}
