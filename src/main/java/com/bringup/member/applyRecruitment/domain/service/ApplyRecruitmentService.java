package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.ApplyRecruitmentErrorCode.NOT_FOUND_APPLY_RECRUITMENT;
import static com.bringup.common.enums.ApplyRecruitmentErrorCode.NOT_FOUND_MEMBER_CV;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_CV;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;

    public ApplyRecruitmentEntity getApplyRecruitment(UserDetailsImpl userDetails, int recruitmentIndex){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));
        CVEntity cv = cvRepository.findByUserIndex(user.getUserIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV));
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));
        ApplyRecruitmentEntity applyRecruitment = applyRecruitmentRepository.findByCvIndexAndRecruitmentIndex(cv.getCvIndex(), recruitment.getRecruitmentIndex())
                .orElseThrow(()->new RuntimeException("지원한 공고가 없습니다."));
        return applyRecruitment;
    }


    public void addApplyRecruitment(UserDetailsImpl userDetails, int recruitmentIndex){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        CVEntity cv = cvRepository.findByUserIndex(user.getUserIndex())
                .orElseThrow(()->new RuntimeException("해당 유저의 이력서를 찾을 수 없습니다."));

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(()->new RuntimeException("해당 공고를 찾을 수 없습니다."));

        ApplyRecruitmentEntity applyRecruitmentEntity = new ApplyRecruitmentEntity();
        applyRecruitmentEntity.setCvIndex(cv.getCvIndex());
        applyRecruitmentEntity.setRecruitmentIndex(recruitment.getRecruitmentIndex());
        applyRecruitmentEntity.setStatus(ApplyCVType.IN_PROGRESS);
        applyRecruitmentRepository.save(applyRecruitmentEntity);
    }

    public List<ApplyRecruitmentResponseDto> getApplyRecruitmentList(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        CVEntity cv = cvRepository.findByUserIndex(user.getUserIndex())
                .orElseThrow(()->new RuntimeException("해당 유저의 이력서를 찾을 수 없습니다."));

        List<ApplyRecruitmentEntity> applyList = applyRecruitmentRepository.findByCvIndex(cv.getCvIndex());

        return applyList.stream()
                .map(apply -> {
                    Recruitment recruitment = recruitmentRepository.findByCompanyCompanyId(apply.getRecruitmentIndex())
                            .orElseThrow(()->new RuntimeException("해당 공고를 찾을 수 없습니다."));
                    Company company = companyRepository.findBycompanyId(recruitment.getCompany().getCompanyId())
                            .orElseThrow(()->new RuntimeException("해당 기업을 찾을 수 없습니다."));
                    return new ApplyRecruitmentResponseDto(apply);
                }).collect(Collectors.toList());
    }
}
