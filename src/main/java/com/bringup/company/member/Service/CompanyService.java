package com.bringup.company.member.Service;

import com.bringup.company.member.DTO.request.JoinDto;
import com.bringup.company.member.DTO.request.LoginDto;
import com.bringup.company.member.DTO.request.ValidationRequestDto;
import com.bringup.company.member.DTO.request.ValidationRequestInfo;
import com.bringup.company.member.DTO.response.LoginTokenDto;
import com.bringup.company.member.DTO.response.ValidationResponseDto;
import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Repository.CompanyRepository;
import com.bringup.company.member.exception.CompanyException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.bringup.common.enums.MemberErrorCode.*;

@Service
public class CompanyService {

    CompanyRepository companyRepository;


    /**
     * 회원 등록
     */
    @Transactional
    public String joinCompany(JoinDto joinDto) {
        // 아이디 중복 체크
        if (companyRepository.existsByUserid(joinDto.getId())) {
            throw new CompanyException(DUPLICATED_MEMBER_EMAIL);
        } else if (companyRepository.existsByEmail(joinDto.getManager_phone())) {
            throw new CompanyException(DUPLICATED_MEMBER_PHONE_NUMBER);
        }

        /*Company company = ConvertUtil.toDtoOrEntity(joinDto, Company.class);
        company.setManagerEmail(joinDto.getId());
        company.setCompanyPassword(passwordEncoder.encode(joinDto.getPassword()));
        company.setCompanyName(joinDto.getCompany_name());
        company.setCompanyPhoneNumber(joinDto.getCompany_phone());
        company.setCompanyAddress(joinDto.getAddress());
        company.setCompanyContent(joinDto.getContent());
        company.setCompanyWelfare(joinDto.getWelfare());
        company.setCompanyVision(joinDto.getVision());
        company.setCompanyHistory(joinDto.getHistory());
        company.setManagerName(joinDto.getManager_name());
        company.setManagerPhoneNumber(joinDto.getManager_phone());
        company.setCompanySize(joinDto.getCompanysize());
        company.setCompanyLogo(joinDto.getLogo());
        company.setOpenCVKey(joinDto.getCv_key());*/

        //String id = companyRepository.save(company).getCompanyName();

        return null;
    }

    /**
     * 로그인
     */
/*    public LoginTokenDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginDto.getUserid(),
                loginDto.getPassword()
        );

        String accessToken = jwtUtil.createJwt("access", userDetail.getId(),
                Collections.singletonList(userDetail.getAuthorities().toString()), TokenExpirationTime.ACCESS_TIME);


        LoginTokenDto loginTokenDto = LoginTokenDto.builder()
                .accessToken(accessToken)
                .id(userDetail.getId)
                .build();
    }*/
}
