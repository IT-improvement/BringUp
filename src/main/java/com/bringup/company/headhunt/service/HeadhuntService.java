package com.bringup.company.headhunt.service;

import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.headhunt.dto.response.HeadhuntResponseDto;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeadhuntService {
    private final CVRepository cvRepository;
    private final RecruitmentRepository recruitmentRepository;

    // 스킬이 하나라도 일치하는 CV를 필터링하고, 그 중 5개를 랜덤으로 추천하는 메서드
    public List<HeadhuntResponseDto> recommendCVsBasedOnCompanySkills(CompanyDetailsImpl userDetails) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByCompanyCompanyId(userDetails.getId());
        List<CVEntity> allCVs = cvRepository.findAll();

        List<CVEntity> matchingCVs = new ArrayList<>();

        // 스킬이 하나라도 일치하는 CV를 필터링
        for (Recruitment recruitment : recruitments) {
            for (CVEntity cv : allCVs) {
                if (hasMatchingSkills(recruitment.getSkill(), cv.getSkill())) {
                    matchingCVs.add(cv);
                }
            }
        }

        // 중복 제거
        matchingCVs = matchingCVs.stream().distinct().collect(Collectors.toList());

        // 5개만 랜덤으로 선택하여 반환
        Random random = new Random();
        List<HeadhuntResponseDto> recommendations = matchingCVs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return recommendations.stream()
                .sorted((a, b) -> random.nextInt(2) - 1) // 랜덤으로 정렬
                .limit(5) // 최대 5개까지 제한
                .collect(Collectors.toList());
    }

    // 모든 CV를 리스트업하는 메서드
    public List<HeadhuntResponseDto> listAllCVs() {
        return cvRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
        return new HeadhuntResponseDto(cvEntity.getCvIndex(), cvEntity.getCvImage(), cvEntity.isMainCv(), cvEntity.getEducation(), cvEntity.getSkill(), cvEntity.getUserIndex(), cvEntity.getStatus());
    }
}