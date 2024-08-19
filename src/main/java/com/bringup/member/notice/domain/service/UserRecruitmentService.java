package com.bringup.member.notice.domain.service;

import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import com.bringup.member.notice.domain.repository.ScrapRecruitmentRepository;
import com.bringup.member.notice.domain.repository.UserRecruitmentRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecruitmentService {

    private final UserRecruitmentRepository userRecruitmentRepository;
    private final UserRepository userRepository;
    private final ScrapRecruitmentRepository scrapRecruitmentRepository;


    // 모든 UserRecruitmentEntity를 조회하는 메서드
    public List<UserRecruitmentEntity> getAllRecruitments() {
        return userRecruitmentRepository.findAll();  // userRecruitmentRepository를 사용해 모든 공고를 조회합니다.
    }

    // 현재 사용자가 북마크한 공고 목록을 가져오는 메서드
    public List<UserRecruitmentEntity> getBookmarkedRecruitments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        String userEmail;

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();  // UserDetails에서 이메일을 가져옵니다.
        } else {
            userEmail = principal.toString();  // UserDetails가 아니면 Principal을 문자열로 변환합니다.
        }
        // 이메일을 사용해 데이터베이스에서 UserEntity를 조회하고, 없으면 예외를 던집니다.
        UserEntity user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        // userIndex에 해당하는 활성화된 스크랩 공고 목록을 조회합니다.
        return scrapRecruitmentRepository.findBookmarkedRecruitmentsByUserIndex(user.getUserIndex());
    }
}