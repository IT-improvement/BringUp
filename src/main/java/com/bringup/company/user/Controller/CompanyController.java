package com.bringup.company.user.Controller;


import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.user.DTO.request.JoinDto;
import com.bringup.company.user.DTO.request.LoginDto;
import com.bringup.company.user.DTO.request.ValidationRequestDto;
import com.bringup.company.user.DTO.response.LoginTokenDto;
import com.bringup.company.user.Entity.Company;
import com.bringup.company.user.Service.CompanyService;
import com.bringup.company.user.Service.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.CREATE;
import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final VerificationService verificationService;

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
        if(isValid == false)
            isValid = true;
        return ResponseEntity.ok().body(new BfResponse<>(SUCCESS, Map.of("isValid", isValid)));
    }

    // 회원가입 2단계 ( 정보 작성 )
    @PostMapping("/join/second")
    public ResponseEntity<BfResponse<?>> registerUser(@RequestPart("joinDto") JoinDto joinDTO,
                                                      @RequestPart("c_logo") MultipartFile logo,
                                                      HttpSession session) {
        ValidationRequestDto businessInfo = (ValidationRequestDto) session.getAttribute("businessInfo");
        if (businessInfo != null) {
            joinDTO.setMaster_name(businessInfo.getP_nm());
            joinDTO.setCompany_opendate(businessInfo.getStart_dt());
            joinDTO.setCompany_licence(businessInfo.getB_no());
        }
        //프로젝트 발표 전까지는 주석처리 ( 진위여부 파악 실패시 회원가입 안됨 )
        //else throw new CompanyException(MemberErrorCode.NOT_FOUND_1st);
        companyService.joinCompany(joinDTO, logo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BfResponse<>(CREATE, Map.of("Company_name", joinDTO.getC_name())));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<BfResponse<LoginTokenDto>> login(@Valid @RequestBody LoginDto loginDto) {
        // 디버그 로그 추가
        System.out.println("Controller: login method called with " + loginDto.getUserid());
        return ResponseEntity.ok(new BfResponse<>(companyService.login(loginDto)));
    }

    // Id 중복 체크
    @PostMapping("/checkId")
    public ResponseEntity<BfResponse<?>> checkId(@RequestBody Map<String, String> requestBody) {
        String companyEmail = requestBody.get("company_email");
        boolean isAvailable = companyService.checkId(companyEmail);
        return ResponseEntity.ok(new BfResponse<>(isAvailable));
    }

    // 기업명 헤더 삽입
    @PostMapping("/companyName")
    public ResponseEntity<BfResponse<?>> companyName(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, companyService.companyName(userDetails)));
    }

    // 회원 정보 수정
    @PutMapping("/user")
    public ResponseEntity<BfResponse<?>> updateUser(
            @AuthenticationPrincipal CompanyDetailsImpl userDetails,
            @RequestParam Map<String, String> requestBody) {
        companyService.updateUser(userDetails, requestBody);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Company update successful")));
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<BfResponse<?>> deleteUser(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        companyService.deleteUser(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "DELETE successful")));
    }

    // 회원 정보 조회
    @GetMapping("/companyInfo/post")
    public ResponseEntity<BfResponse<Company>> getUserInfo(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        Company company = companyService.getUserInfo(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, company));
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
