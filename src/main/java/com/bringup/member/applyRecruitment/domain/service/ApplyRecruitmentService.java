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
import com.bringup.member.applyRecruitment.dto.request.ApplyRecruitmentResponseDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.ApplyRecruitmentErrorCode.*;
import static com.bringup.common.enums.ApplyRecruitmentErrorCode.NOT_FOUND_MEMBER_ID;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void setApplyRecruitment(UserDetailsImpl userDetails, ApplyRecruitmentRequestDto dto){
        CVEntity cv = cvRepository.findById(dto.getCvIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV));

        if (!userDetails.getId().equals(cv.getUserIndex())){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_ID);
        }

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(dto.getRecruitmentIndex())
                .orElseThrow(()->new ApplyRecruitmentException(NOT_FOUND_APPLY_RECRUITMENT));

        ApplyRecruitmentEntity applyRecruitment = new ApplyRecruitmentEntity();
        applyRecruitment.setCvIndex(cv.getCvIndex());
        applyRecruitment.setRecruitmentIndex(recruitment.getRecruitmentIndex());
        applyRecruitment.setApplicationType(dto.getApplicationType());
        applyRecruitment.setStatus(ApplyCVType.IN_PROGRESS);

        applyRecruitmentRepository.save(applyRecruitment);
    }

    @Transactional
    public List<ApplyRecruitmentResponseDto> getApplyCVList(UserDetailsImpl userDetails){
        List<CVEntity> cvList = cvRepository.findAllByUserIndex(userDetails.getId());

        if (cvList.isEmpty()){
            throw new ApplyRecruitmentException(NOT_FOUND_MEMBER_CV);
        }

        List<ApplyRecruitmentResponseDto> applyList = new ArrayList<>();

        for (CVEntity cv : cvList){
            List<ApplyRecruitmentEntity> applyRecruitments = applyRecruitmentRepository.findAllByCvIndex(cv.getCvIndex());
            for (ApplyRecruitmentEntity applyRecruitment : applyRecruitments){
                if (applyRecruitment.getApplicationType() != ApplicationType.FREELANCER){
                    ApplyRecruitmentResponseDto dto = applyDto(applyRecruitment);
                    applyList.add(dto);
                }
            }
        }
        return applyList;
    }

    private ApplyRecruitmentResponseDto applyDto(ApplyRecruitmentEntity applyRecruitment){
        return ApplyRecruitmentResponseDto.builder()
                .applyCVIndex(applyRecruitment.getApplyCVIndex())
                .recruitmentIndex(applyRecruitment.getRecruitmentIndex())
                .applicationType(applyRecruitment.getApplicationType())
                .status(applyRecruitment.getStatus())
                .applyCVDate(applyRecruitment.getApplyCVDate())
                .build();
    }
}
