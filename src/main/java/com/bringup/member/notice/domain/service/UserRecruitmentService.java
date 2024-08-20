package com.bringup.member.notice.domain.service;

import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import com.bringup.member.notice.domain.repository.ScrapRecruitmentRepository;
import com.bringup.member.notice.domain.repository.UserRecruitmentRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRecruitmentService {

    private final UserRecruitmentRepository userRecruitmentRepository;
    private final UserRepository userRepository;
    private final ScrapRecruitmentRepository scrapRecruitmentRepository;


    // 모든 UserRecruitmentEntity를 조회하는 메서드
    public List<UserRecruitmentEntity> getAllRecruitments() {
        return userRecruitmentRepository.findAll();  // userRecruitmentRepository를 사용해 모든 공고를 조회합니다.
    }

    public List<UserRecruitmentEntity> getBookmarkedRecruitments() {
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
        List<UserRecruitmentEntity> recruitments = scrapRecruitmentRepository.findBookmarkedRecruitmentsByUserIndex(user.getUserIndex());

        // 조회된 스크랩 공고 목록을 로그로 출력
        log.info("Found {} bookmarked recruitments for User Index: {}", recruitments.size(), user.getUserIndex());

        return recruitments;
    }
}