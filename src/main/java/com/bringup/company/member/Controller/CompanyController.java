package com.bringup.company.member.Controller;


import com.bringup.common.response.BfResponse;
import com.bringup.company.member.DTO.request.JoinDto;
import com.bringup.company.member.DTO.request.LoginDto;
import com.bringup.company.member.DTO.request.ValidationRequestDto;
import com.bringup.company.member.DTO.response.LoginTokenDto;
import com.bringup.company.member.Service.CompanyService;
import com.bringup.company.member.Service.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            @RequestBody Map<String, String> payload, HttpSession session) {
        ValidationRequestDto businessNumberValidateRequestDto = ValidationRequestDto.builder()
                .b_no(payload.get("company_licence"))
                .start_dt(payload.get("company_opendate"))
                .p_nm(payload.get("master_name"))
                .build();

        boolean isValid = verificationService.verifyCompanyInfo(businessNumberValidateRequestDto);
        if (isValid) {
            session.setAttribute("businessInfo", businessNumberValidateRequestDto);
        }
        return ResponseEntity.ok().body(new BfResponse<>(SUCCESS, Map.of("isValid", isValid)));
    }

    // 회원가입 2단계 ( 정보 작성 )
    @PostMapping("/join/second")
    public ResponseEntity<BfResponse<?>> registerUser(@RequestBody JoinDto joinDTO, HttpSession session) {
        ValidationRequestDto businessInfo = (ValidationRequestDto) session.getAttribute("businessInfo");
        if (businessInfo != null) {
            joinDTO.setMaster_name(businessInfo.getP_nm());
            joinDTO.setCompany_opendate(businessInfo.getStart_dt());
            joinDTO.setCompany_licence(businessInfo.getB_no());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BfResponse<>(CREATE, Map.of("Company_name", companyService.joinCompany(joinDTO))));
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

    // CompanyName 헤더에 삽입 ( UserDetailsImpl이 작성되어있다는 가정하에 작성 )
    /*@PostMapping("/companyName")
    public ResponseEntity<BfResponse<?>> companyName(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(new BfResponse<>(userDetails.getCompanyName));
    }*/


}
