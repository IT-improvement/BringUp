package com.bringup.company.member.Service;

import com.bringup.common.jwt.JWTUtil;
import com.bringup.company.member.DTO.request.CompanyDetails;
import com.bringup.company.member.DTO.request.JoinDto;
import com.bringup.company.member.DTO.request.LoginDto;
import com.bringup.company.member.DTO.response.LoginTokenDto;
import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Repository.CompanyRepository;
import com.bringup.company.member.exception.CompanyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 등록
     */
    @Transactional
    public Company joinCompany(JoinDto joinDto) {
        // DTO 데이터를 사용하여 엔티티 생성 및 기본값 설정
        Company company = new Company();

        company.setManagerEmail(joinDto.getId());
        company.setCompanyPassword(passwordEncoder.encode(joinDto.getPassword()));
        company.setCompanyPhonenumber(joinDto.getCompany_phone());
        company.setCompanyName(joinDto.getCompany_name());
        company.setManagerName(joinDto.getManager_name());
        company.setManagerPhonenumber(joinDto.getManager_phone());
        company.setCompanyAddress(joinDto.getCompany_address());
        company.setCompanyCategory(joinDto.getCompany_category());
        company.setCompanyContent(joinDto.getCompany_content());
        company.setCompanyWelfare(joinDto.getCompany_welfare());
        company.setCompanyHistory(joinDto.getCompany_history());
        company.setCompanyScale(joinDto.getCompany_scale());
        company.setCompanyVision(joinDto.getCompany_vision());
        company.setCompanyLogo(joinDto.getCompany_logo());
        company.setCompanySize(joinDto.getCompany_size());
        company.setCompany_Opendate(joinDto.getCompany_opendate());
        company.setCompanyLicense(joinDto.getCompany_licence());
        company.setMasterName(joinDto.getMaster_name());

        // 기본값 설정
        company.setRole("COMPANY"); // 기본 Role 설정
        company.setOpencvKey(0); // 이력서 열람 키 기본값 설정
        company.setStatus("ACTIVE"); // 회사 상태 기본값 설정

        return companyRepository.save(company);
    }


    /**
     * 로그인
     */
    public LoginTokenDto login(LoginDto loginDto) {
        // 디버그 로그 추가
        System.out.println("Service: login method called with " + loginDto.getUserid());

        if (loginDto == null) {
            throw new IllegalArgumentException("loginDto cannot be null");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUserid(),
                loginDto.getPassword()
        );

        // 디버그 로그 추가
        System.out.println("Service: authentication token created for " + loginDto.getUserid());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CompanyDetails companyDetails = (CompanyDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.createJwt(
                companyDetails.getUsername(),
                companyDetails.getAuthorities().toString(),
                60 * 60 * 10L // 10시간 동안 유효한 토큰
        );

        // 디버그 로그 추가
        System.out.println("Service: JWT token created for " + companyDetails.getUsername());

        return LoginTokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    /**
     * ID(companyEmail) Check
     */
    public boolean checkId(String company_email){
        log.debug("user id : " + company_email);
        return !companyRepository.existsByManagerEmail(company_email);
    }


}
