package com.bringup.member.review.service;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.entity.CompanyReview;
import com.bringup.company.review.repository.CompanyReviewRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.review.dto.request.RequestCompanyReviewDto;
import com.bringup.member.review.dto.response.MemberCompanyReviewDto;
import com.bringup.member.review.dto.response.MemberDetailReviewDto;
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

    public MemberDetailReviewDto getReviewDetail(int reviewId) {
        // 리뷰 ID로 리뷰를 조회, 없을 시 예외 처리
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을수 없습니다. " + reviewId));

        // CompanyReview 엔티티를 MemberCompanyReviewDto로 변환
        return convertDetailToDto(review);
    }



    // 리뷰 수정
    @Transactional
    public void updateCompanyReview(UserDetailsImpl userDetails,  int reviewId, RequestCompanyReviewDto reviewDto) {
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을수 없습니다. " + userDetails.getUsername()));

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
        // 현재 날짜와 시간을 "년-월-일 시:분" 형식으로 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        review.setCompanyReviewDate(formattedDateTime);



        companyReviewRepository.save(review);
    }

    @Transactional
    public void deleteCompanyReview(UserDetailsImpl userDetails, int reviewId) {
        // 리뷰 ID로 리뷰를 조회
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("리뷰를 삭제할 권한이 없습니다.");
        }

        companyReviewRepository.delete(review);
    }

    // CompanyReview 엔티티를 MemberCompanyReviewDto로 변환하는 메서드
    private MemberDetailReviewDto convertDetailToDto(CompanyReview review) {
        MemberDetailReviewDto dto = new MemberDetailReviewDto();

        dto.setCompanyReviewIndex(review.getCompanyReviewIndex());
        dto.setCompanyName(review.getCompany().getCompanyName()); // 회사 이름 설정
        dto.setUserEmail(review.getUser().getUserEmail()); // 사용자 이메일 설정
        dto.setAdvancement(review.getAdvancement());
        dto.setBenefit(review.getBenefit());
        dto.setManagement(review.getManagement());
        dto.setContent(review.getContent());
        dto.setWorkLife(review.getWorkLife());
        dto.setCompanyCulture(review.getCompanyCulture());
        dto.setContent(review.getContent());
        dto.setCompanyReviewTitle(review.getCompanyReviewTitle());
        dto.setCompanyReviewDate(review.getCompanyReviewDate());

        // 각 항목의 점수로 평균을 계산
        double averageRating = (review.getAdvancement()
                + review.getBenefit()
                + review.getWorkLife()
                + review.getCompanyCulture()
                + review.getManagement()) / 5.0;
        dto.setAverageRating(averageRating); // 평균 점수를 DTO에 설정

        return dto;
    }


    // CompanyReview 엔티티를 MemberCompanyReviewDto로 변환하는 메서드
    private MemberCompanyReviewDto convertToDto(CompanyReview review) {
        MemberCompanyReviewDto dto = new MemberCompanyReviewDto();

        dto.setCompanyReviewIndex(review.getCompanyReviewIndex());
        dto.setCompanyName(review.getCompany().getCompanyName()); // 회사 이름 설정
        dto.setUserEmail(review.getUser().getUserEmail()); // 사용자 이메일 설정
        dto.setContent(review.getContent());
        dto.setCompanyReviewTitle(review.getCompanyReviewTitle());
        dto.setCompanyReviewDate(review.getCompanyReviewDate());

        // 각 항목의 점수로 평균을 계산
        double averageRating = (review.getAdvancement()
                + review.getBenefit()
                + review.getWorkLife()
                + review.getCompanyCulture()
                + review.getManagement()) / 5.0;
        dto.setAverageRating(averageRating); // 평균 점수를 DTO에 설정

        return dto;
    }
}