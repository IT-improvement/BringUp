package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.common.enums.ApplyRecruitmentErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.recruitment.repository.RecruitmentFreelancerRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentFreelancerRepository;
import com.bringup.member.applyRecruitment.dto.request.ApplyFreelancerRequestDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.entity.CVFree;
import com.bringup.member.resume.domain.repository.CVFreeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bringup.common.enums.ApplyRecruitmentErrorCode.*;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentFreelancerService {
    private final ApplyRecruitmentFreelancerRepository applyRecruitmentFreelancerRepository;
    private final CVFreeRepository cvFreeRepository;
    private final RecruitmentFreelancerRepository recruitmentFreelancerRepository;

    @Transactional
    public void setApplyRecruitmentFreelancer(UserDetailsImpl userDetails, ApplyFreelancerRequestDto dto){
        CVFree cvFree = cvFreeRepository.findById(dto.getCvIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV));

        if (!userDetails.getId().equals(cvFree.getCvIndex())){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_ID);
        }

        RecruitmentFreelancer recruitmentFreelancer = recruitmentFreelancerRepository.findById(dto.getProjectIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

        ApplyRecruitmentEntity applyRecruitmentFreelancer = new ApplyRecruitmentEntity();
        applyRecruitmentFreelancer.setApplyCVIndex(cvFree.getCvIndex());
        applyRecruitmentFreelancer.setRecruitmentIndex(recruitmentFreelancer.getProjectIndex());
        applyRecruitmentFreelancer.setApplicationType(ApplicationType.FREELANCER);
        applyRecruitmentFreelancer.setStatus(ApplyCVType.IN_PROGRESS);

        applyRecruitmentFreelancerRepository.save(applyRecruitmentFreelancer);
    }
}
