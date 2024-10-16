package com.bringup.company.user.service;

import com.bringup.common.enums.CertificateErrorCode;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.event.Service.CertificateService;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.jwt.JwtProvider;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.user.dto.request.JoinDto;
import com.bringup.company.user.dto.request.LoginDto;
import com.bringup.company.user.dto.request.SalaryDto;
import com.bringup.company.user.dto.request.UpdateImageRequestDto;
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
import java.util.*;

import static com.bringup.common.enums.CertificateErrorCode.INVALID_CERTIFCATE_NUMBER;
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
    public Company joinCompany(JoinDto joinDto, MultipartFile logo, MultipartFile[] imgs) {

        String email = joinDto.getM_phone(); // 원래 이메일이였어서 일단 이메일로 두는데 확실시되면 폰으로 바꿈

        try {
            // 이메일 인증 여부 확인
            if (!certificateService.isVerified(email)) {
                throw new CertificateException(INVALID_CERTIFCATE_NUMBER);
            }

            // 이메일 중복 체크
            if (companyRepository.existsByManagerPhonenumber(email)) {
                throw new CompanyException(DUPLICATED_MEMBER_EMAIL);
            }
        } catch (Exception e) {
            log.error("Validation failed: {}", e.getMessage());
            throw new CompanyException(CHECK_ID_OR_PASSWORD);
        }

        Company company = new Company();

        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        company.setManagerEmail(joinDto.getC_email());
        company.setCompanyPassword(encodedPassword);
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
        company.setCompanySize(joinDto.getC_size());
        company.setCompanyLogo(imageService.saveImage(logo));
        company.setCompanyImg(imageService.uploadImages(imgs));
        company.setCompanyOpendate(joinDto.getCompany_opendate());
        company.setCompanyLicense(joinDto.getCompany_licence());
        company.setMasterName(joinDto.getMaster_name());
        company.setCompanyHomepage(joinDto.getC_homePage());
        company.setCompanySubsidiary(joinDto.getSubsidiary());
        company.setCompanyFinancialStatements(joinDto.getFinancial_stat());
        company.setOpencvKey(joinDto.getCv_key());

        company.setRole(RolesType.ROLE_COMPANY);
        company.setStatus(StatusType.ACTIVE);

        Company savedCompany = companyRepository.save(company);

        if (joinDto.getSalaries() != null) {
            for (SalaryDto salaryDto : joinDto.getSalaries()) {
                saveSalary(savedCompany.getCompanyId(), salaryDto);
            }
        }

        certificateService.deleteVerificationTokenByIdentifier(email);

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

        try{
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            System.out.println("userDetails : " + userDetails);

            String accessToken = jwtProvider.createAccessToken(userDetails);
            System.out.println("Service: JWT token created for " + userDetails.getUsername());

            return LoginTokenDto.builder()
                    .accessToken(accessToken)
                    .build();
        } catch (AdvertisementException e){
            throw new CompanyException(CHECK_ID_OR_PASSWORD);
        }

    }

    /**
     * ID(companyEmail) Check
     */
    public boolean checkId(String company_email) {
        try {
            return companyRepository.existsByManagerEmail(company_email);
        } catch (Exception e) {
            log.error("Error checking ID: {}", e.getMessage());
            throw new CompanyException(NOT_FOUND_MEMBER_EMAIL);
        }
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
                List<Map<String, String>> salaryList = new ObjectMapper().readValue(salariesJson, new TypeReference<List<Map<String, String>>>() {
                });
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
                log.error("Error updating salaries: {}", e.getMessage());
                throw new CompanyException(INVALID_REQUEST_FORMAT);
            }
        }
    }

    public void updateUserImages(int userId, MultipartFile logo, UpdateImageRequestDto updateImageRequestDto) throws IOException {
        // 회사 정보 가져오기
        Company company = companyRepository.findBycompanyId(userId)
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_EMAIL));

        // 로고 처리
        if (logo != null && !logo.isEmpty()) {
            String logoPath = imageService.saveImage(logo);
            company.setCompanyLogo(logoPath);  // 로고 경로 업데이트
        }

        // 기존 이미지 경로 리스트로 변환
        List<String> existingImages = new ArrayList<>(Arrays.asList(company.getCompanyImg().split(",")));

        // 새로운 이미지를 처리하고, 이미지를 앞으로 당기는 로직 적용
        processAndRearrangeImages(updateImageRequestDto, existingImages);

        // 수정된 이미지 경로 업데이트
        company.setCompanyImg(String.join(",", existingImages));

        // 회사 정보 업데이트
        companyRepository.save(company);
    }

    // 이미지 상태에 따라 이미지를 처리하고 재정렬하는 메소드
    private void processAndRearrangeImages(UpdateImageRequestDto dto, List<String> existingImages) throws IOException {
        // 클라이언트에서 받은 이미지와 상태 목록
        List<String> newImages = Arrays.asList(dto.getC_img0(), dto.getC_img1(), dto.getC_img2(), dto.getC_img3(), dto.getC_img4());
        List<String> imageStatuses = Arrays.asList(dto.getImg0_status(), dto.getImg1_status(), dto.getImg2_status(), dto.getImg3_status(), dto.getImg4_status());

        // 최종 이미지 리스트
        List<String> finalImageList = new ArrayList<>();

        for (int i = 0; i < newImages.size(); i++) {
            String newImage = newImages.get(i);
            String status = imageStatuses.get(i);

            // 클라이언트에서 보낸 값이 없고 상태가 delete라면 기존 이미지 삭제
            if (newImage == null && "deleted".equals(status) && i < existingImages.size()) {
                existingImages.set(i, null);  // 삭제된 이미지를 null로 설정
            }

            // status가 modified일 때, 기존 이미지 경로와 비교하여 기존 이미지라면 그대로 유지
            else if ("modified".equals(status) && newImage != null) {
                if (isBase64Image(newImage)) {
                    // 새 이미지가 Base64로 인코딩된 경우 처리
                    String imagePath = saveBase64Image(newImage, i);
                    finalImageList.add(imagePath);
                } else if (existingImages.size() > i && existingImages.get(i) != null && existingImages.get(i).equals(newImage)) {
                    // 기존 경로와 같다면 새로 저장하지 않고 경로만 유지
                    finalImageList.add(existingImages.get(i));
                }
            }

            // 삭제 요청이 아닌 기존 이미지가 있으면 그대로 유지
            else if (newImage == null && i < existingImages.size() && existingImages.get(i) != null) {
                finalImageList.add(existingImages.get(i));
            }
        }

        // 이미지 리스트에서 빈 값(null)을 제거하고 순서를 앞으로 당긴다
        finalImageList.removeIf(img -> img == null || img.isEmpty());

        // 기존 이미지 리스트를 업데이트된 리스트로 대체
        existingImages.clear();
        existingImages.addAll(finalImageList);
    }

    // Base64 이미지인지 확인하는 메소드
    private boolean isBase64Image(String imageString) {
        return imageString.contains(",") && imageString.split(",").length == 2;
    }

    // Base64 이미지를 디코딩하고 저장하는 메소드
    private String saveBase64Image(String base64ImageString, int index) throws IOException {
        String[] parts = base64ImageString.split(",");
        String mimeType = parts[0];  // "image/jpeg"
        String base64Data = parts[1];  // Base64 인코딩된 이미지 데이터

        // 저장할 파일 이름 설정
        String fileExtension = mimeType.split("/")[1];  // "jpeg" 같은 확장자 추출
        String fileName = "company_img" + index + "." + fileExtension;

        // Base64 데이터를 파일로 저장
        return imageService.saveBase64Image(base64Data, fileName);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(UserDetailsImpl userDetails) {
        try{
            Company company = companyRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new CompanyException(FORBIDDEN_DELETE_MEMBER));

            company.setStatus(StatusType.INACTIVE);

            companyRepository.save(company);
        }catch (Exception e){
            throw new CompanyException(FORBIDDEN_DELETE_MEMBER);
        }
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
            throw new CompanyException(NOT_FOUND_MEMBER_EMAIL);
        }
    }
}