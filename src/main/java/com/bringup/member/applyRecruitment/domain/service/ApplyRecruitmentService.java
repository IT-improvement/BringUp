package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.recruitment.repository.RecruitmentFreelancerRepository;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.applyRecruitment.dto.request.ApplyRecruitmentRequestDto;
import com.bringup.member.applyRecruitment.dto.request.ApplyRecruitmentResponseDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bringup.common.enums.ApplyRecruitmentErrorCode.*;
import static com.bringup.common.enums.ApplyRecruitmentErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentFreelancerRepository freelancerRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void setApplyRecruitment(UserDetailsImpl userDetails, ApplyRecruitmentRequestDto dto){
        CVEntity cv = cvRepository.findById(dto.getCvIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV));

        if (!userDetails.getId().equals(cv.getUserIndex())){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_ID);
        }

        switch (dto.getRecruitmentType()){
            case RECRUITMENT:
                Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(dto.getRecruitmentIndex())
                        .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

                if (applyRecruitmentRepository.existsByCv_CvIndexAndRecruitmentIndex(cv.getCvIndex(), recruitment.getRecruitmentIndex())){
                    throw new ApplyRecruitmentException(ALREADY_APPLY_RECRUITMENT);
                }

                ApplyRecruitmentEntity applyRecruitment = new ApplyRecruitmentEntity();
                applyRecruitment.setCv(cv);
                applyRecruitment.setRecruitmentIndex(recruitment.getRecruitmentIndex());
                applyRecruitment.setApplicationType(ApplicationType.RECRUITMENT);
                applyRecruitment.setStatus(ApplyCVType.IN_PROGRESS);

                applyRecruitmentRepository.save(applyRecruitment);
                break;

            case FREELANCER:
                RecruitmentFreelancer freelancer = freelancerRepository.findByProjectIndex(dto.getRecruitmentIndex())
                        .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

                if (applyRecruitmentRepository.existsByCv_CvIndexAndRecruitmentIndex(cv.getCvIndex(), freelancer.getProjectIndex())){
                    throw new ApplyRecruitmentException(ALREADY_APPLY_RECRUITMENT);
                }

                ApplyRecruitmentEntity applyFreelancer = new ApplyRecruitmentEntity();
                applyFreelancer.setCv(cv);
                applyFreelancer.setRecruitmentIndex(freelancer.getProjectIndex());
                applyFreelancer.setApplicationType(ApplicationType.FREELANCER);
                applyFreelancer.setStatus(ApplyCVType.IN_PROGRESS);

                applyRecruitmentRepository.save(applyFreelancer);
                break;

            default:
                throw new ApplyRecruitmentException(INVALID_REQUEST_FORMAT);
        }
    }

    @Transactional
    public List<ApplyRecruitmentResponseDto> getApplyCVList(UserDetailsImpl userDetails){
        List<CVEntity> cvList = cvRepository.findAllByUserIndex(userDetails.getId());

        if (cvList.isEmpty()){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV);
        }

        List<ApplyRecruitmentResponseDto> applyList = new ArrayList<>();

        for (CVEntity cv : cvList) {
            // 이력서와 연관된 모든 지원 내역 조회
            List<ApplyRecruitmentEntity> applyRecruitments = applyRecruitmentRepository.findAllByCv_CvIndex(cv.getCvIndex());

            for (ApplyRecruitmentEntity applyRecruitment : applyRecruitments) {
                if (applyRecruitment.getApplicationType() == ApplicationType.RECRUITMENT) {
                    // 일반 공고 정보 조회
                    Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(applyRecruitment.getRecruitmentIndex())
                            .orElseThrow(() -> new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

                    // 회사 정보 조회
                    String companyName = recruitment.getCompany().getCompanyName();

                    // DTO 생성 및 추가
                    ApplyRecruitmentResponseDto dto = applyDto(applyRecruitment, recruitment.getRecruitmentTitle(), companyName);
                    applyList.add(dto);

                } else if (applyRecruitment.getApplicationType() == ApplicationType.FREELANCER) {
                    // 프리랜서 공고 정보 조회
                    RecruitmentFreelancer freelancer = freelancerRepository.findByProjectIndex(applyRecruitment.getRecruitmentIndex())
                            .orElseThrow(() -> new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

                    // 회사 정보 조회 (프리랜서 공고와 연관된 회사)
                    String companyName = freelancer.getCompany().getCompanyName();

                    // DTO 생성 및 추가
                    ApplyRecruitmentResponseDto dto = applyDto(applyRecruitment, freelancer.getProjectTitle(), companyName);
                    applyList.add(dto);
                }
            }
        }
        return applyList;
    }

    private ApplyRecruitmentResponseDto applyDto(ApplyRecruitmentEntity applyRecruitment, String recruitmentTitle, String companyName){
        return ApplyRecruitmentResponseDto.builder()
                .applyCVIndex(applyRecruitment.getApplyCVIndex())
                .cv(applyRecruitment.getCv())
                .recruitmentIndex(applyRecruitment.getRecruitmentIndex())
                .applicationType(applyRecruitment.getApplicationType())
                .status(applyRecruitment.getStatus())
                .applyCVDate(applyRecruitment.getApplyCVDate())
                .recruitmentTitle(recruitmentTitle)
                .companyName(companyName)
                .build();
    }
}
