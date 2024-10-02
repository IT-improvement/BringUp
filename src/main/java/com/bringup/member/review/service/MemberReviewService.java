package com.bringup.member.review.service;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.entity.CompanyReview;
import com.bringup.company.review.repository.CompanyReviewRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.review.dto.request.RequestCompanyReviewDto;
import com.bringup.member.review.dto.response.MemberCompanyReviewDto;
import com.bringup.member.review.exception.MemberReviewException;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReviewService {

    private final CompanyReviewRepository companyReviewRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    // 사용자 리뷰 리스트를 가져오는 서비스
    public List<MemberCompanyReviewDto> getCompanyReviews() {
        // 사용자의 회사 리뷰를 가져옴
        List<CompanyReview> reviews = companyReviewRepository.findAll();
        // DTO로 변환하여 리스트에 추가
        List<MemberCompanyReviewDto> reviewDtos = new ArrayList<>();
        for (CompanyReview review : reviews) {
            MemberCompanyReviewDto dto = convertToDto(review);
            reviewDtos.add(dto);
        }
        return reviewDtos;
    }

    @Transactional
    public void createCompanyReview(UserDetailsImpl userDetails, RequestCompanyReviewDto reviewDto) {

        // UserDetails에서 이메일을 통해 로그인한 사용자 정보 조회
        UserEntity user = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 사용자를 찾을 수 없습니다: " + userDetails.getUsername()));

        // 회사 이름으로 회사 정보 조회
        Company company = companyRepository.findByCompanyName(reviewDto.getCompanyName())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사 이름으로 회사를 찾을 수 없습니다: " + reviewDto.getCompanyName()));

        // 새로운 리뷰 엔티티 생성 및 정보 설정
        CompanyReview review = new CompanyReview();
        review.setUser(user);  // UserEntity 설정
        review.setCompany(company);  // Company 설정

        // 리뷰 정보 DTO에서 가져와 설정
        review.setAdvancement(reviewDto.getAdvancement());
        review.setBenefit(reviewDto.getBenefit());
        review.setWorkLife(reviewDto.getWorkLife());
        review.setCompanyCulture(reviewDto.getCompanyCulture());
        review.setManagement(reviewDto.getManagement());
        review.setContent(reviewDto.getContent());
        review.setCompanyReviewTitle(reviewDto.getCompanyReviewTitle());

        // 현재 날짜와 시간을 "년-월-일 시:분" 형식으로 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        review.setCompanyReviewDate(formattedDateTime);

        // 기본 상태값을 "ACTIVE"로 설정
        review.setStatus("ACTIVE");

        // 리뷰를 데이터베이스에 저장
        companyReviewRepository.save(review);
    }

    // 리뷰 수정
    @Transactional
    public void updateCompanyReview(UserDetailsImpl userDetails, MemberCompanyReviewDto reviewDto) {
        CompanyReview review = companyReviewRepository.findById(reviewDto.getCompanyReviewIndex())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + userDetails.getUsername()));

        if (!review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("리뷰를 수정할 권한이 없습니다.");
        }

        review.setAdvancement(reviewDto.getAdvancement());
        review.setBenefit(reviewDto.getBenefit());
        review.setWorkLife(reviewDto.getWorkLife());
        review.setCompanyCulture(reviewDto.getCompanyCulture());
        review.setManagement(reviewDto.getManagement());
        review.setContent(reviewDto.getContent());
        review.setCompanyReviewTitle(reviewDto.getCompanyReviewTitle());
        review.setCompanyReviewDate(reviewDto.getCompanyReviewDate());

        companyReviewRepository.save(review);
    }
/*

    // 리뷰 삭제
    @Transactional
    public void deleteCompanyReview(UserDetailsImpl userDetails) {
        CompanyReview review = companyReviewRepository.fin  dByUser(userDetails.getUser())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("리뷰를 삭제할 권한이 없습니다.");
        }

        companyReviewRepository.delete(review);
    }
*/



    // CompanyReview 엔티티를 MemberCompanyReviewDto로 변환하는 메서드
    private MemberCompanyReviewDto convertToDto(CompanyReview review) {
        MemberCompanyReviewDto dto = new MemberCompanyReviewDto();
        dto.setCompanyReviewIndex(review.getCompanyReviewIndex());
        dto.setAdvancement(review.getAdvancement());
        dto.setBenefit(review.getBenefit());
        dto.setWorkLife(review.getWorkLife());
        dto.setCompanyCulture(review.getCompanyCulture());
        dto.setManagement(review.getManagement());
        dto.setContent(review.getContent());
        dto.setCompanyReviewTitle(review.getCompanyReviewTitle());
        dto.setCompanyReviewDate(review.getCompanyReviewDate());
        return dto;
    }
}