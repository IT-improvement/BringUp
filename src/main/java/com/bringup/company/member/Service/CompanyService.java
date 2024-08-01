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
    private final CompanyUserDetailsService companyUserDetailsService;


    /**
     * 회원 등록
     */
    @Transactional
    public String joinCompany(JoinDto joinDto) {
        // 아이디 중복 체크
        if (companyRepository.existsByManagerEmail(joinDto.getId())) {
            throw new CompanyException(DUPLICATED_MEMBER_EMAIL);
        } else if (companyRepository.existsByManagerPhonenumber(joinDto.getManager_phone())) {
            throw new CompanyException(DUPLICATED_MEMBER_PHONE_NUMBER);
        }

        Company company = new Company();
        company.setManagerEmail(joinDto.getId());
        company.setCompanyPassword(passwordEncoder.encode(joinDto.getPassword()));
        company.setCompanyName(joinDto.getCompany_name());
        company.setCompanyPhonenumber(joinDto.getCompany_phone());
        company.setCompanyAdress(joinDto.getAddress());
        company.setCompanyContent(joinDto.getContent());
        company.setCompanyWelfare(joinDto.getWelfare());
        company.setCompanyVision(joinDto.getVision());
        company.setCompanyHistory(joinDto.getHistory());
        company.setManagerName(joinDto.getManager_name());
        company.setManagerPhonenumber(joinDto.getManager_phone());
        company.setCompanySize(joinDto.getCompanysize());
        company.setCompanyLogo(joinDto.getLogo());
        company.setOpencvKey(joinDto.getCv_key());

        companyRepository.save(company);
        return company.getCompanyName();
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
