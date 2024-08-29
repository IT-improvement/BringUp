package com.bringup.company.user.service;

import com.bringup.common.enums.CertificateErrorCode;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.event.Service.CertificateService;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.jwt.JwtProvider;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.user.dto.request.JoinDto;
import com.bringup.company.user.dto.request.LoginDto;
import com.bringup.company.user.dto.request.SalaryDto;
import com.bringup.company.user.dto.response.LoginTokenDto;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.entity.Salary;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.company.user.repository.SalaryRepository;
import com.bringup.company.user.exception.CompanyException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SalaryRepository salaryRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CertificateService certificateService;
    private final ImageService imageService;

    /**
     * 회원 등록
     */
    @Transactional
    public Company joinCompany(JoinDto joinDto, MultipartFile logo) {

        String email = joinDto.getId();

        // 이메일 인증 여부 확인
        if (!certificateService.isEmailVerified(email)) {
            throw new CertificateException(CertificateErrorCode.INVALID_CERTIFCATE_NUMBER);
        }

        // 이메일 중복 체크
        if (companyRepository.existsByManagerEmail(email)) {
            throw new CompanyException(MemberErrorCode.DUPLICATED_MEMBER_EMAIL);
        }

        Company company = new Company();

        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());
        System.out.println("Encoded Password: " + encodedPassword);

        company.setManagerEmail(email);
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
        company.setCompanyLogo(imageService.upLoadImage(logo));
        company.setCompanySize(joinDto.getC_size());
        company.setCompanyOpendate(joinDto.getCompany_opendate());
        company.setCompanyLicense(joinDto.getCompany_licence());
        company.setMasterName(joinDto.getMaster_name());
        company.setCompanyHomepage(joinDto.getC_homePage());
        company.setCompanySubsidiary(joinDto.getSubsidiary());
        company.setCompanyFinancialStatements(joinDto.getFinancial_stat());
        company.setOpencvKey(joinDto.getCv_key());

        company.setRole(RolesType.ROLE_COMPANY);
        company.setStatus("활성");

        Company savedCompany = companyRepository.save(company);

        if (joinDto.getSalaries() != null) {
            for (SalaryDto salaryDto : joinDto.getSalaries()) {
                saveSalary(savedCompany.getCompanyId(), salaryDto);
            }
        }

        certificateService.deleteVerificationTokenByEmail(email);

        return savedCompany;
    }

/*    private String saveLogoImage(MultipartFile logo) {
        if (logo.isEmpty()) {
            return null;
        }

        try {
            // 이미지 저장 경로 지정
            String uploadDir = "src/main/resources/static/logos/";
            String fileName = "Logo_" + UUID.randomUUID().toString() + "_" + logo.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, logo.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store logo image", e);
        }
    }*/

    private void saveSalary(int companyId, SalaryDto salaryDto) {
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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUserid(),
                loginDto.getPassword()
        );

        System.out.println("Service: login method called with " + loginDto.getUserid());
        System.out.println("authenticationToken : " + authenticationToken);

        if (loginDto == null) {
            throw new IllegalArgumentException("loginDto cannot be null");
        }

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("userDetails : " + userDetails);

        String accessToken = jwtProvider.createAccessToken(userDetails);
        System.out.println("Service: JWT token created for " + userDetails.getUsername());

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
    public void updateUser(UserDetailsImpl userDetails, Map<String, String> requestBody) {
        Company company = companyRepository.findById(userDetails.getId())
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
    public void deleteUser(UserDetailsImpl userDetails) {
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_EMAIL));

        companyRepository.delete(company);
    }

    public Company getUserInfo(UserDetailsImpl userDetails) {
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_EMAIL));
    }

    public String companyName(UserDetailsImpl userDetails) {
        Optional<Company> companyOptional = companyRepository.findByManagerEmail(userDetails.getUsername());
        if (companyOptional.isPresent()) {
            return companyOptional.get().getCompanyName();
        } else {
            throw new IllegalArgumentException("Company not found for username: " + userDetails.getUsername());
        }
    }
}