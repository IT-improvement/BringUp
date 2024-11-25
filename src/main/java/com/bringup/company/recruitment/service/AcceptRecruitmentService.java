/*
package com.bringup.company.recruitment.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.response.AcceptCVResponseDto;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.portfolio.career.domain.CareerEntity;
import com.bringup.member.portfolio.career.domain.CareerRepository;
import com.bringup.member.portfolio.career.domain.CareerService;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcceptRecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final CVRepository cvRepository;
    private final UserRepository userRepository;
    private final CareerService careerService;

*/
/*    public List<AcceptCVResponseDto> getVolunteerListInCompany(UserDetailsImpl userDetails) {
        List<Recruitment> recruitment = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());

        List<Integer> recruitmentIdx = recruitment.stream()
                .map(Recruitment::getRecruitmentIndex)
                .toList();

        List<ApplyRecruitmentEntity> apply = applyRecruitmentRepository.findAllByRecruitmentIndexIn(recruitmentIdx);

        // ApplyRecruitmentEntity 데이터를 AcceptCVResponseDto로 변환
        return apply.stream()
                .map(application -> {
                    // cvIndex를 통해 CVEntity 조회
                    CVEntity cvEntity = cvRepository.findByCvIndex(application.getCvIndex());
                    UserEntity user = userRepository.findByUserIndex(cvEntity.getUserIndex())
                            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

                    // DTO로 매핑
                    return AcceptCVResponseDto.builder()
                            .cvIdx(cvEntity.getCvIndex())
                            .cv_name(user.getUserName())
                            .cv_history(String.valueOf(careerService.getListCareer(user.getUserIndex())))
                            .cv_date(String.valueOf(application.getApplyCVDate()))
                            .build();
                })
                .collect(Collectors.toList());
    }*//*


    public List<AcceptCVResponseDto> getVolunteerListInRecruitment(int recruitmentIdx) {

        List<ApplyRecruitmentEntity> apply = applyRecruitmentRepository.findAllByRecruitmentIndex(recruitmentIdx);


        // ApplyRecruitmentEntity 데이터를 AcceptCVResponseDto로 변환
        return apply.stream()
                .map(application -> {
                    // cvIndex를 통해 CVEntity 조회
                    CVEntity cvEntity = cvRepository.findByCvIndex(application.getCvIndex());
                    UserEntity user = userRepository.findByUserIndex(cvEntity.getUserIndex())
                            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

                    // DTO로 매핑
                    return AcceptCVResponseDto.builder()
                            .cvIdx(cvEntity.getCvIndex())
                            .cv_name(user.getUserName())
                            .cv_history(String.valueOf(careerService.getListCareer(user.getUserIndex())))
                            .cv_date(String.valueOf(application.getApplyCVDate()))
                            .build();
                })
                .collect(Collectors.toList());
    }
}
*/
