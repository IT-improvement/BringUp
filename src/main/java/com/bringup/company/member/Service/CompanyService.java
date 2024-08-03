package com.bringup.company.member.Service;

import com.bringup.common.jwt.JWTUtil;
import com.bringup.company.member.DTO.request.CompanyDetails;
import com.bringup.company.member.DTO.request.JoinDto;
import com.bringup.company.member.DTO.request.LoginDto;
import com.bringup.company.member.DTO.request.SalaryDto;
import com.bringup.company.member.DTO.response.LoginTokenDto;
import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Entity.Salary;
import com.bringup.company.member.Repository.CompanyRepository;
import com.bringup.company.member.Repository.SalaryRepository;
import com.bringup.company.member.exception.CompanyException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SalaryRepository salaryRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper

    /**
     * 회원 등록
     */
    @Transactional
    public Company joinCompany(JoinDto joinDto) {
        Company company = new Company();

        company.setManagerEmail(joinDto.getId());
        company.setCompanyPassword(passwordEncoder.encode(joinDto.getPassword()));
        company.setCompanyPhonenumber(joinDto.getC_phone());
        company.setCompanyName(joinDto.getC_name());
        company.setManagerName(joinDto.getM_name());
        company.setManagerPhonenumber(joinDto.getM_phone());
        company.setCompanyAddress(joinDto.getC_address());
        company.setCompanyCategory(joinDto.getC_category());
        company.setCompanyContent(joinDto.getC_content());
        company.setCompanyWelfare(joinDto.getWelfare_benefits());
        company.setCompanyHistory(joinDto.getC_history());
        company.setCompanyScale(joinDto.getC_scale());
        company.setCompanyVision(joinDto.getC_vision());
        company.setCompanyLogo(joinDto.getC_logo());
        company.setCompanySize(joinDto.getC_size());
        company.setCompanyOpendate(joinDto.getCompany_opendate());
        company.setCompanyLicense(joinDto.getCompany_licence());
        company.setMasterName(joinDto.getMaster_name());
        company.setCompanyHomepage(joinDto.getC_homePage());
        company.setCompanySubsidiary(joinDto.getSubsidiary());
        company.setCompanyFinancialStatements(joinDto.getFinancial_stat());
        company.setOpencvKey(joinDto.getCv_key());

        company.setRole("COMPANY");
        company.setStatus("ACTIVE");

        Company savedCompany = companyRepository.save(company);

        if (joinDto.getSalaries() != null) {
            for (SalaryDto salaryDto : joinDto.getSalaries()) {
                saveSalary(savedCompany.getCompanyId(), salaryDto);
            }
        }

        return savedCompany;
    }

    private void saveSalary(Long companyId, SalaryDto salaryDto) {
        Salary salary = new Salary();
        salary.setCompanyId(companyId);
        salary.setPosition(salaryDto.getPosition());
        salary.setMinSalary(salaryDto.getMinSalary());
        salary.setMaxSalary(salaryDto.getMaxSalary());
        salary.setAverageSalary((salaryDto.getMinSalary() + salaryDto.getMaxSalary()) / 2);
        salaryRepository.save(salary);
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

    // 회원 정보 수정
    @Transactional
    public void updateUser(String token, Map<String, String> requestBody) {
        String username = jwtUtil.getUsername(token);
        Company company = companyRepository.findByManagerEmail(username)
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_EMAIL));

        company.setManagerEmail(requestBody.get("m_email"));
        company.setManagerPhonenumber(requestBody.get("manager_num"));
        company.setCompanyAddress(requestBody.get("c_address"));
        company.setCompanyCategory(requestBody.get("sectors"));
        company.setCompanyName(requestBody.get("c_name"));
        company.setCompanyContent(requestBody.get("c_intro"));
        company.setCompanyHomepage(requestBody.get("c_homepage"));
        company.setCompanyVision(requestBody.get("c_vision"));
        company.setCompanyContent(requestBody.get("business_info"));
        company.setCompanyHistory(requestBody.get("c_history"));
        company.setCompanySubsidiary(requestBody.get("subsidiary"));
        company.setCompanyWelfare(requestBody.get("welfare_benefits"));
        company.setCompanySize(Integer.parseInt(requestBody.get("staff_size")));
        company.setCompanyFinancialStatements(requestBody.get("financial_statements"));

        companyRepository.save(company);

        if (requestBody.containsKey("salaries")) {
            String salariesJson = requestBody.get("salaries");
            try {
                List<Map<String, String>> salaryList = new ObjectMapper().readValue(salariesJson, new TypeReference<List<Map<String, String>>>() {});
                for (Map<String, String> salaryMap : salaryList) {
                    String position = salaryMap.get("position");
                    Optional<Salary> existingSalaryOpt = salaryRepository.findByCompanyIdAndPosition(company.getCompanyId(), position);
                    Salary salary = existingSalaryOpt.orElse(new Salary());
                    salary.setCompanyId(company.getCompanyId());
                    salary.setPosition(position);
                    salary.setMinSalary(Integer.parseInt(salaryMap.get("minSalary")));
                    salary.setMaxSalary(Integer.parseInt(salaryMap.get("maxSalary")));
                    salary.setAverageSalary((Integer.parseInt(salaryMap.get("minSalary")) + Integer.parseInt(salaryMap.get("maxSalary"))) / 2);
                    salaryRepository.save(salary);
                }
            } catch (Exception e) {
                throw new CompanyException(INVALID_REQUEST_FORMAT);
            }
        }
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(String token) {
        String username = jwtUtil.getUsername(token);
        Company company = companyRepository.findByManagerEmail(username)
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_EMAIL));

        companyRepository.delete(company);
    }

    /*// 로그아웃
    public void logout(String token, Map<String, String> requestBody) {
        // 로그아웃 처리 로직 (예: 토큰 무효화, 세션 종료 등)
        // 현재는 JWT 기반이므로 서버 측 로그아웃 처리가 불필요.
        // 필요한 경우 추가 구현.
    }*/
}
