package com.bringup.member.review.service;

import com.bringup.company.review.entity.InterviewReview;
import com.bringup.company.review.repository.InterviewReviewRepository;
import com.bringup.member.review.dto.request.InterviewReviewRequestDto;
import com.bringup.member.review.dto.response.InterviewReviewResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewReviewService {
    private final InterviewReviewRepository interviewReviewRepository;

    // 리뷰 리스트 조회
    public List<InterviewReviewResponseDto> getAllReviews() {
        List<InterviewReview> reviews = interviewReviewRepository.findAll();
        List<InterviewReviewResponseDto> responseDtoList = new ArrayList<>();

        for (InterviewReview review : reviews) {
            InterviewReviewResponseDto responseDto = convertToDto(review);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    // 리뷰 작성
    @Transactional
    public void createReview(InterviewReviewRequestDto requestDto) {
        InterviewReview review = new InterviewReview();

        review.setCompanyIndex(requestDto.getCompanyIndex());
        review.setUserIndex(requestDto.getUserIndex());
        review.setAmbience(Integer.parseInt(requestDto.getAmbience()));
        review.setDifficulty(requestDto.getDifficulty());
        review.setInterviewReviewTitle(requestDto.getInterviewReviewTitle());
        review.setInterviewReviewContent(requestDto.getInterviewReviewContent());
        review.setStatus("ACTIVE");

        interviewReviewRepository.save(review);
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(int reviewId, InterviewReviewRequestDto requestDto) {
        InterviewReview review = interviewReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        review.setAmbience(Integer.parseInt(requestDto.getAmbience()));
        review.setDifficulty(requestDto.getDifficulty());
        review.setInterviewReviewTitle(requestDto.getInterviewReviewTitle());
        review.setInterviewReviewContent(requestDto.getInterviewReviewContent());

        interviewReviewRepository.save(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(int reviewId) {
        InterviewReview review = interviewReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        interviewReviewRepository.delete(review);
    }

    // Entity -> DTO 변환 메서드
    private InterviewReviewResponseDto convertToDto(InterviewReview review) {
        InterviewReviewResponseDto responseDto = new InterviewReviewResponseDto();

        responseDto.setInterviewReviewIndex(review.getInterviewReviewIndex());
        responseDto.setCompanyIndex(review.getCompanyIndex());
        responseDto.setUserIndex(review.getUserIndex());
        responseDto.setAmbience(String.valueOf(review.getAmbience()));
        responseDto.setDifficulty(review.getDifficulty());
        responseDto.setInterviewReviewTitle(review.getInterviewReviewTitle());
        responseDto.setInterviewReviewDate(review.getInterviewReviewDate().toString()); // Assuming it's a LocalDateTime
        responseDto.setInterviewReviewContent(review.getInterviewReviewContent());
        responseDto.setStatus(review.getStatus());

        return responseDto;
    }
}