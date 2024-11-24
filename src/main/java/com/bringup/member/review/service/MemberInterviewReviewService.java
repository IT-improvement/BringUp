package com.bringup.member.review.service;

import com.bringup.common.enums.ReviewErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.entity.CompanyReview;
import com.bringup.company.review.entity.InterviewReview;
import com.bringup.company.review.repository.InterviewReviewRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.review.dto.request.InterviewReviewRequestDto;
import com.bringup.member.review.dto.response.InterviewReviewResponseDto;
import com.bringup.member.review.exception.MemberReviewException;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberInterviewReviewService {

    private final InterviewReviewRepository interviewReviewRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    // 인터뷰 리뷰 리스트 조회
    public List<InterviewReviewResponseDto> getAllReviews() {
        List<InterviewReview> reviews = interviewReviewRepository.findAll();
        List<InterviewReviewResponseDto> responseDtoList = new ArrayList<>();

        for (InterviewReview review : reviews) {
            InterviewReviewResponseDto responseDto = convertToDto(review);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

  // 인터뷰 리뷰 작성
    @Transactional
    public void createReview(UserDetailsImpl userDetails, InterviewReviewRequestDto requestDto) {
        // UserDetails에서 이메일을 통해 로그인한 사용자 정보 조회
        UserEntity user = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 사용자를 찾을 수 없습니다: " + userDetails.getUsername()));

        // 회사 이름으로 회사 정보 조회
        Company company = companyRepository.findByCompanyName(requestDto.getCompanyName())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사 이름으로 회사를 찾을 수 없습니다: " + requestDto.getCompanyName()));


        InterviewReview review = new InterviewReview();
        review.setUser(user);
        review.setCompany(company);
        review.setAmbience(Integer.parseInt(requestDto.getAmbience()));
        review.setDifficulty(requestDto.getDifficulty());
        review.setInterviewReviewTitle(requestDto.getInterviewReviewTitle());
        review.setInterviewReviewContent(requestDto.getInterviewReviewContent());

        // 현재 날짜와 시간을 "년-월-일 시:분" 형식으로 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        review.setInterviewReviewDate(formattedDateTime);

        review.setStatus("ACTIVE");

        interviewReviewRepository.save(review);
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(UserDetailsImpl userDetails, int reviewId, InterviewReviewRequestDto requestDto) {
        // 리뷰 ID로 해당 리뷰를 조회
        InterviewReview review = interviewReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        // 현재 로그인한 사용자가 해당 리뷰를 작성한 사람인지 확인
        if (!review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("리뷰를 수정할 권한이 없습니다.");
        }

        // 리뷰 정보 수정
        review.setAmbience(Integer.parseInt(requestDto.getAmbience()));
        review.setDifficulty(requestDto.getDifficulty());
        review.setInterviewReviewTitle(requestDto.getInterviewReviewTitle());
        // 현재 날짜와 시간을 "년-월-일 시:분" 형식으로 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        review.setInterviewReviewDate(formattedDateTime);

        review.setInterviewReviewContent(requestDto.getInterviewReviewContent());

        // 수정된 리뷰를 저장
        interviewReviewRepository.save(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(UserDetailsImpl userDetails, int reviewId) {
        InterviewReview review = interviewReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 삭제 권한 확인
        if (!review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("리뷰를 삭제할 권한이 없습니다.");
        }

        interviewReviewRepository.delete(review);
    }

    // Entity -> DTO 변환 메서드
    private InterviewReviewResponseDto convertToDto(InterviewReview review) {
        InterviewReviewResponseDto responseDto = new InterviewReviewResponseDto();

        responseDto.setInterviewReviewIndex(review.getInterviewReviewIndex());
        responseDto.setAmbience(review.getAmbience());
        responseDto.setDifficulty(review.getDifficulty());
        responseDto.setInterviewReviewTitle(review.getInterviewReviewTitle());
        responseDto.setInterviewReviewDate(review.getInterviewReviewDate().toString()); // Assuming it's a LocalDateTime
        responseDto.setInterviewReviewContent(review.getInterviewReviewContent());

        return responseDto;
    }

    public InterviewReviewResponseDto getMostStar(int companyIdx){
        Pageable pageable = PageRequest.of(0, 1); // 첫 번째 리뷰만 가져오기
        List<InterviewReview> reviews = interviewReviewRepository.findTopByAverageRating(companyIdx, pageable);

        if (reviews.isEmpty()) {
            throw new MemberReviewException(ReviewErrorCode.NOT_FOUND_REVIEW);
        }

        InterviewReview review = reviews.get(0);

        // DTO로 매핑
        return convertToDto(review);
    }

    // 특정 기업의 리뷰 리스트 조회
    public List<InterviewReviewResponseDto> getAllReviewsByCompany(int companyId) {
        // 특정 기업의 리뷰 조회
        List<InterviewReview> reviews = interviewReviewRepository.findAllByCompanyCompanyId(companyId);

        // DTO 리스트로 변환
        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}