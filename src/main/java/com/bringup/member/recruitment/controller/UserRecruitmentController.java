package com.bringup.member.recruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.response.RecruitmentDetailResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.member.recruitment.domain.service.UserRecruitmentService;
import com.bringup.member.recruitment.dto.response.RecruitmentVisitResponseDto;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDetailDto;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDto;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/recruitment")
@RequiredArgsConstructor
public class UserRecruitmentController {


    private final UserRecruitmentService userRecruitmentService;
    private final ErrorResponseHandler errorResponseHandler;


    @GetMapping("/list")
    public ResponseEntity<List<UserRecruitmentDto>> getAllRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getAllRecruitments();
        return ResponseEntity.ok(recruitments); // JSON 형식으로 반환
    }



    @GetMapping("/detail/{recruitmentIndex}")
    public ResponseEntity<BfResponse<?>> getRecruitmentDetail(@PathVariable("recruitmentIndex") int recruitmentId) {
        try {
            UserRecruitmentDetailDto recruitmentDetail = userRecruitmentService.getRecruitmentDetail(recruitmentId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitmentDetail));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/topList")
    public ResponseEntity<BfResponse<?>> getTopRecruitments() {
        try {
            List<UserRecruitmentDto> recruitments = userRecruitmentService.getTopRecruitments();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitments));
        }catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    // 북마크 추가
    @PostMapping("/scrap/{recruitmentIndex}")
    public ResponseEntity<String> addBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable("recruitmentIndex") Integer recruitmentIndex) {
        Integer userIndex = userDetails.getId(); // 인증된 유저의 Index 가져오기
        userRecruitmentService.addBookmark(userIndex, recruitmentIndex);
        return ResponseEntity.ok("북마크가 추가되었습니다.");
    }
    // 북마크 삭제
    @DeleteMapping("/scrap/delete/{recruitmentIndex}")
    public ResponseEntity<String> removeBookmark(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable("recruitmentIndex") Integer recruitmentIndex) {
        Integer userIndex = user.getId(); // 인증된 유저의 Index 가져오기
        userRecruitmentService.removeBookmark(userIndex, recruitmentIndex);
        return ResponseEntity.ok("북마크가 삭제되었습니다.");
    }
    //북마크 확인
    @GetMapping("/isBookmarked/{recruitmentIndex}")
    public ResponseEntity<Map<String, Boolean>> isBookmarked(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("recruitmentIndex") Integer recruitmentId) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean isBookmarked = userRecruitmentService.isBookmarked(user.getId(), recruitmentId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("bookmarked", isBookmarked);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/visit/{recruitmentIndex}")
    public ResponseEntity<BfResponse<?>> addRecruitmentVisit(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("recruitmentIndex") int recruitmentIndex){
        try {
            userRecruitmentService.addRecruitmentVisit(userDetails, recruitmentIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "save visit recruitment"));
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visitList")
    public ResponseEntity<BfResponse<?>> getVisitRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            List<RecruitmentVisitResponseDto> visitDto = userRecruitmentService.getVisitRecruitment(userDetails);
            return ResponseEntity.ok(new BfResponse<>(visitDto));
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}