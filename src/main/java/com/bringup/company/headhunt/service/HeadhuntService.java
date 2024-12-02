package com.bringup.company.headhunt.service;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.bookmark.domain.repository.CompanyBookMarkRepository;
import com.bringup.common.bookmark.exception.BookmarkException;
import com.bringup.common.enums.BookmarkType;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.headhunt.dto.response.HeadhuntDetailResponseDto;
import com.bringup.company.headhunt.dto.response.HeadhuntResponseDto;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.membership.domain.repository.UserMembershipRepository;
import com.bringup.member.resume.domain.entity.CVAward;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.*;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.membership.domain.entity.UserMembership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class HeadhuntService {
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyBookMarkRepository companyBookMarkRepository;
    private final UserRepository userRepository;
    private final UserMembershipRepository userMembershipRepository;
    private final CVAwardRepository cvAwardRepository;
    private final CVBlogRepository cvBlogRepository;
    private final CVCareerRepository cvCareerRepository;
    private final CVCertificateRepository cvCertificateRepository;
    private final CVFreeRepository cvFreeRepository;
    private final CVSchoolRepository cvSchoolRepository;

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
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_ID));

        // 주소에서 OO시 OO동만 추출
        String fullAddress = user.getUserAddress();
        String[] addressParts = fullAddress.split(" ");
        String userAddress = addressParts[0] + " " + addressParts[1]; // OO시 OO동 추출

        String userName = user.getUserName();
        String[] userNameParts = userName.split(" ");
        String userNames = userNameParts[0];

        return new HeadhuntResponseDto(
                cvEntity.getCvIndex(),
                cvEntity.isMainCv(),
                cvEntity.getSkill(),
                userAddress, // 가공된 주소
                cvEntity.getUserIndex(), // 유저 인덱스 추가
                userNames // 필터링된 이름값
        );
    }

    @Transactional
    public List<HeadhuntResponseDto> listSavedCVs(UserDetailsImpl userDetails) {
        Company company = companyRepository.findBycompanyId(userDetails.getId())
                .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID));

        // 저장된 유저만 가져오기 (BookmarkType.VOLUNTEER)
        List<CompanyBookMarkEntity> bookmarks = companyBookMarkRepository.findByCompanyAndStatus(company, BookmarkType.VOLUNTEER);

        // 북마크된 유저를 DTO로 변환하여 반환
        return bookmarks.stream()
                .map(bookmark -> {
                    // 유저 인덱스를 통해 이력서(CV)와 유저 정보 가져오기
                    CVEntity cv = cvRepository.findByUserIndexAndAndMainCvTrue(bookmark.getUser().getUserIndex())
                            .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID));
                    UserEntity user = userRepository.findById(cv.getUserIndex())
                            .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID));

                    // HeadhuntResponseDto로 변환 (CV와 유저 정보를 사용)
                    return new HeadhuntResponseDto(
                            cv.getCvIndex(),         // 이력서 인덱스
                            cv.isMainCv(),           // 메인 이력서 여부
                            cv.getSkill(),           // 기술 정보
                            user.getUserAddress(),   // 유저 주소
                            user.getUserIndex(),     // 유저 인덱스
                            user.getUserName()       // 유저 이름
                    );
                })
                .collect(Collectors.toList());
    }

    public HeadhuntDetailResponseDto getHeadhuntDetail(int cvIdx){
        return null;

    }

}
