package com.bringup.company.headhunt.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.headhunt.dto.response.HeadhuntResponseDto;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.member.membership.domain.repository.UserMembershipRepository;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.membership.domain.entity.UserMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HeadhuntService {
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final UserMembershipRepository userMembershipRepository;

    // 멤버십 유저 중 스킬이 일치하는 5명의 유저를 랜덤으로 추천하는 메서드 (mainCv가 true인 경우만 포함)
    public List<HeadhuntResponseDto> recommendMembershipCVsBasedOnCompanySkills(UserDetailsImpl userDetails) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());
        List<CVEntity> allCVs = cvRepository.findAll();
        Set<Integer> membershipUserIds = new HashSet<>();

        // 멤버십 유저 인덱스를 모음
        List<UserMembership> userMemberships = userMembershipRepository.findAll();
        for (UserMembership userMembership : userMemberships) {
            membershipUserIds.add(userMembership.getUserIndex());
        }

        List<CVEntity> matchingCVs = new ArrayList<>();

        // 스킬이 하나라도 일치하는 멤버십 유저의 CV를 필터링 (mainCv가 true인 경우만 포함)
        for (Recruitment recruitment : recruitments) {
            for (CVEntity cv : allCVs) {
                if (membershipUserIds.contains(cv.getUserIndex()) && hasMatchingSkills(recruitment.getSkill(), cv.getSkill()) && cv.isMainCv()) {
                    matchingCVs.add(cv);
                }
            }
        }

        // 중복 제거
        Set<CVEntity> distinctCVs = new HashSet<>(matchingCVs);
        matchingCVs.clear();
        matchingCVs.addAll(distinctCVs);

        // 5개만 랜덤으로 선택하여 반환
        Random random = new Random();
        List<HeadhuntResponseDto> recommendations = new ArrayList<>();
        List<CVEntity> randomSelection = new ArrayList<>();

        while (randomSelection.size() < 5 && !matchingCVs.isEmpty()) {
            int randomIndex = random.nextInt(matchingCVs.size());
            randomSelection.add(matchingCVs.remove(randomIndex));
        }

        for (CVEntity cvEntity : randomSelection) {
            recommendations.add(convertToDto(cvEntity));
        }

        return recommendations;
    }

    // 모든 일반 유저들의 CV 리스트를 반환하는 메서드
    public List<HeadhuntResponseDto> listAllCVs() {
        Set<Integer> membershipUserIds = new HashSet<>();

        // 멤버십 유저 인덱스를 모음
        List<UserMembership> userMemberships = userMembershipRepository.findAll();
        for (UserMembership userMembership : userMemberships) {
            membershipUserIds.add(userMembership.getUserIndex());
        }

        List<UserEntity> allUsers = userRepository.findAll();
        List<HeadhuntResponseDto> allCVs = new ArrayList<>();

        for (UserEntity user : allUsers) {
            if (!membershipUserIds.contains(user.getUserIndex())) { // 멤버십 유저가 아닌 일반 유저들만 필터링
                List<CVEntity> userCVs = cvRepository.findAllByUserIndex(user.getUserIndex());
                for (CVEntity cvEntity : userCVs) {
                    if (cvEntity.isMainCv()) {  // mainCv가 true인 경우만 추가
                        allCVs.add(convertToDto(cvEntity));
                    }
                }
            }
        }

        return allCVs;
    }

    // 스킬 매칭 여부 확인
    private boolean hasMatchingSkills(String recruitmentSkills, String cvSkills) {
        String[] recruitmentSkillArray = recruitmentSkills.split(",");
        String[] cvSkillArray = cvSkills.split(",");

        for (String recruitmentSkill : recruitmentSkillArray) {
            for (String cvSkill : cvSkillArray) {
                if (recruitmentSkill.trim().equalsIgnoreCase(cvSkill.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    private HeadhuntResponseDto convertToDto(CVEntity cvEntity) {
        UserEntity user = userRepository.findById(cvEntity.getUserIndex())
                .orElseThrow(() -> new CompanyException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        // 주소에서 OO시 OO동만 추출
        String fullAddress = user.getUserAddress();
        String[] addressParts = fullAddress.split(" ");
        String userAddress = addressParts[0] + " " + addressParts[1]; // OO시 OO동 추출

        String userName = user.getUserName();
        String[] userNameParts = userName.split(" ");
        String userNames = userNameParts[0];

        return new HeadhuntResponseDto(
                cvEntity.getCvIndex(),
                cvEntity.getCvImage(),
                cvEntity.isMainCv(),
                cvEntity.getEducation(),
                cvEntity.getSkill(),
                userAddress, // 가공된 주소
                cvEntity.getUserIndex(), // 유저 인덱스 추가
                userNames // 필터링된 이름값
        );
    }
}