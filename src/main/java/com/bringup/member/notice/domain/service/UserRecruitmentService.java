package com.bringup.member.notice.domain.service;

import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.member.notice.domain.repository.ScrapRecruitmentRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.notice.dto.response.UserRecruitmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bringup.company.recruitment.repository.RecruitmentRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRecruitmentService {

    private final RecruitmentRepository userRecruitmentRepository;
    private final UserRepository userRepository;
    private final ScrapRecruitmentRepository scrapRecruitmentRepository;

    // 모든 UserRecruitmentEntity를 조회하는 메서드
    public List<UserRecruitmentDto> getAllRecruitments() {
        List<Recruitment> recruitments = userRecruitmentRepository.findAll();  // 모든 공고를 조회
        List<UserRecruitmentDto> dtoList = new ArrayList<>();  // DTO 리스트 생성
        for (Recruitment recruitment : recruitments) {  // 각 엔티티를 순회
            UserRecruitmentDto dto = convertToDto(recruitment);  // 엔티티를 DTO로 변환
            dtoList.add(dto);  // DTO 리스트에 추가
        }
        return dtoList;  // DTO 리스트 반환
    }
    public List<UserRecruitmentDto> getBookmarkedRecruitments() {
        // 현재 인증된 사용자 정보(Principal)를 가져옵니다.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail;

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();  // UserDetails에서 이메일을 가져옵니다.
        } else {
            userEmail = principal.toString();  // UserDetails가 아니면 Principal을 문자열로 변환합니다.
        }

        // 이메일을 사용해 데이터베이스에서 UserEntity를 조회하고, 없으면 예외를 던집니다.
        UserEntity user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        // userIndex를 로그로 출력하여 확인
        log.info("User Index: {}", user.getUserIndex());

        // userIndex에 해당하는 스크랩 공고 목록을 조회합니다.
        List<Recruitment> recruitments = scrapRecruitmentRepository.findBookmarkedRecruitmentsByUserIndex(user.getUserIndex());

        // 조회된 스크랩 공고 목록을 로그로 출력
        log.info("Found {} bookmarked recruitments for User Index: {}", recruitments.size(), user.getUserIndex());

        // 엔티티를 DTO로 변환
        List<UserRecruitmentDto> dtoList = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            UserRecruitmentDto dto = convertToDto(recruitment);
            dtoList.add(dto);
        }

        return dtoList;
    }
    // Recruitment 엔티티를 UserRecruitmentDto로 변환하는 메서드
    private UserRecruitmentDto convertToDto(Recruitment recruitment) {
        UserRecruitmentDto dto = new UserRecruitmentDto();
        dto.setRecruitmentIndex(recruitment.getRecruitmentIndex());
        dto.setCompanyId(BigInteger.valueOf(recruitment.getCompany().getCompanyId()));
        dto.setRecruitmentType(recruitment.getRecruitmentType().name());
        dto.setCategory(recruitment.getCategory());
        dto.setSkill(recruitment.getSkill());
        dto.setStartDate(recruitment.getStartDate());
        dto.setPeriod(recruitment.getPeriod());
        dto.setStatus(recruitment.getStatus());
        return dto;
    }
}
