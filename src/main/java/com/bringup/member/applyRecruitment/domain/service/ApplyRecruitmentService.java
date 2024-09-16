package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;

    public ApplyRecruitmentEntity getApplyRecruitment(UserDetailsImpl userDetails){
        //applyRecruitmentRepository.findByCvIndexAndRecruitmentIndex(userDetails.);
        return null;
    }

    public List<ApplyRecruitmentResponseDto> applyRecruitmentList(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));
        return null;
    }
}
