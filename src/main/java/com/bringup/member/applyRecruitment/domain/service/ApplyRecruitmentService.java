package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RecruitmentErrorCode;
import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.applyRecruitment.dto.request.ApplyRecruitmentRequestDto;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.bringup.member.recruitment.exception.RecruitmentException;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.ApplyRecruitmentErrorCode.*;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final UserRepository userRepository;
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void addRecruitment(UserDetailsImpl userDetails, int recruitmentIndex, ApplyRecruitmentRequestDto dto){
        CVEntity cv = cvRepository.findByUserIndex(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_CV));

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(()->new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_RECRUITMENT));

        // 이미 지원한 공고가 있는지 검사
        if (applyRecruitmentRepository.existsByCvIndexAndRecruitmentIndex(cv.getCvIndex(), recruitment.getRecruitmentIndex())){
            throw new ApplyRecruitmentException(ALREADY_APPLY_RECRUITMENT);
        }

        ApplyRecruitmentEntity applyRecruitment = ApplyRecruitmentEntity.builder()
                .cvIndex(cv)
                .recruitmentIndex(recruitment)
                .applicationType(dto.getApplicationType())
                .build();

        applyRecruitmentRepository.save(applyRecruitment);
    }

    @Transactional
    public void delRecruitment(UserDetailsImpl userDetails, int cvIndex, int recruitmentIndex){
        ApplyRecruitmentEntity applyRecruitment = applyRecruitmentRepository.findByCvIndexAndRecruitmentIndex(cvIndex ,recruitmentIndex)
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

        if (applyRecruitment.getCvIndex().getUserIndex() != userDetails.getId()){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV);
        }

        applyRecruitmentRepository.delete(applyRecruitment);
    }
}
