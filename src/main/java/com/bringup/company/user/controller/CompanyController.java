package com.bringup.company.user.controller;


import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.user.dto.request.JoinDto;
import com.bringup.company.user.dto.request.LoginDto;
import com.bringup.company.user.dto.request.UpdateImageRequestDto;
import com.bringup.company.user.dto.request.ValidationRequestDto;
import com.bringup.company.user.dto.response.LoginTokenDto;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.service.CompanyService;
import com.bringup.company.user.service.VerificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.CREATE;
import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class CompanyController {
    private final CompanyService companyService;
    private final VerificationService verificationService;
    private final ErrorResponseHandler errorResponseHandler;

    //회원가입 1단계 ( 진위여부 파악 )
    @PostMapping("/join/first")
    public ResponseEntity<BfResponse<?>> validateBusinessNumber(
            @RequestBody Map<String, String> payload /*, HttpSession session*/) {
        ValidationRequestDto businessNumberValidateRequestDto = ValidationRequestDto.builder()
                .b_no(payload.get("company_licence"))
                .start_dt(payload.get("company_opendate"))
                .p_nm(payload.get("master_name"))
                .build();

        boolean isValid = verificationService.verifyCompanyInfo(businessNumberValidateRequestDto);
        /*if (isValid) {
            session.setAttribute("businessInfo", businessNumberValidateRequestDto);
        }*/
        System.out.println("isValid : " + isValid);
        if(!isValid)
            isValid = true;
        return ResponseEntity.ok().body(new BfResponse<>(SUCCESS, Map.of("isValid", isValid)));
    }

    // 회원가입 2단계 ( 정보 작성 )
    @PostMapping("/join/second")
    public ResponseEntity<BfResponse<?>> registerUser(@RequestPart("joinDto") JoinDto joinDTO,
                                                      @RequestPart("c_logo") MultipartFile logo,
                                                      @RequestPart("c_img") MultipartFile[] imgs,
                                                      HttpSession session) {
        ValidationRequestDto businessInfo = (ValidationRequestDto) session.getAttribute("businessInfo");
        try{
            if (businessInfo != null) {
                joinDTO.setMaster_name(businessInfo.getP_nm());
                joinDTO.setCompany_opendate(businessInfo.getStart_dt());
                joinDTO.setCompany_licence(businessInfo.getB_no());
            }
            //프로젝트 발표 전까지는 주석처리 ( 진위여부 파악 실패시 회원가입 안됨 )
            //else throw new CompanyException(MemberErrorCode.NOT_FOUND_1st);
            companyService.joinCompany(joinDTO, logo, imgs);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BfResponse<>(CREATE, Map.of("Company_name", joinDTO.getC_name())));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (CertificateException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<BfResponse<?>> login(@RequestBody LoginDto loginDto) {
        try {
            // 로그인 로직 수행
            LoginTokenDto loginToken = companyService.login(loginDto);
            return ResponseEntity.ok(new BfResponse<>(loginToken));
        } catch (CompanyException e) {
            // CompanyException이 발생했을 때 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            // 기타 예외 처리 (500 내부 서버 오류)
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // Id 중복 체크
    @PostMapping("/checkId")
    public ResponseEntity<BfResponse<?>> checkId(@RequestBody Map<String, String> requestBody) {

            String companyEmail = requestBody.get("company_email");
            if (companyEmail == null){
                return errorResponseHandler.handleErrorResponse(GlobalErrorCode.VALIDATION_FAILED);
            }
        try{
            boolean isAvailable = companyService.checkId(companyEmail);
            return ResponseEntity.ok(new BfResponse<>(isAvailable));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    // 기업명 헤더 삽입
    @PostMapping("/companyName")
    public ResponseEntity<BfResponse<?>> companyName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, companyService.companyName(userDetails)));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    // 회원 정보 수정
    @PutMapping("/user")
    public ResponseEntity<BfResponse<?>> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Map<String, String> requestBody) {
        try{
            companyService.updateUser(userDetails, requestBody);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "업데이트 완료")));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    // 회원 이미지 수정
    @PutMapping("/user/image")
    public ResponseEntity<BfResponse<?>> updateUserImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "c_logo", required = false) MultipartFile logo,  // 새로 업로드된 로고 파일
            @RequestBody UpdateImageRequestDto updateImageRequestDto
    ) {
        try {
            // 서비스 호출을 통해 새로운 파일과 기존 경로 처리
            companyService.updateUserImages(userDetails.getId(), logo, updateImageRequestDto);

            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "업데이트 완료"));
        } catch (CompanyException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (IOException e){
            System.out.println("이미지 저장 에러");
            throw new RuntimeException(e);
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<BfResponse<?>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            companyService.deleteUser(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "탈퇴 완료")));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    // 회원 정보 조회
    @GetMapping("/companyInfo/post")
    public ResponseEntity<BfResponse<?>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            Company company = companyService.getUserInfo(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, company));
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 로그아웃
    /*@PostMapping("/logout")
    public ResponseEntity<BfResponse<?>> logout(
            @RequestHeader("Authorization") String token,
            @RequestParam Map<String, String> requestBody) {
        companyService.logout(token, requestBody);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Logout successful")));
    }*/

}
