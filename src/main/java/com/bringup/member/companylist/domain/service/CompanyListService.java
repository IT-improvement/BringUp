package com.bringup.member.companylist.domain.service;

import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.companylist.dto.response.CompanyListResponseDto;
import com.bringup.member.companylist.dto.response.CompanyUserResponseDto;
import com.bringup.member.companylist.exception.CompanyListException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class CompanyListService {
    private final CompanyRepository companyRepository;

    public List<CompanyListResponseDto> getAllCompany(){
        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty()){
            throw new CompanyListException(NOT_FOUND_MEMBER_EMAIL);
        }

        return companies.stream()
                .map(company -> {
                    return CompanyListResponseDto.builder()
                            .companyId(company.getCompanyId())
                            .companyName(company.getCompanyName())
                            .companyCategory(company.getCompanyCategory())
                            .companyScale(company.getCompanyScale())
                            .companyLogo(company.getCompanyLogo())
                            .build();
                }).collect(Collectors.toList());
    }

    public CompanyUserResponseDto getCompanyDetails(int companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->new CompanyListException(NOT_FOUND_MEMBER_ID));

        return convertDto(company);
    }

    private CompanyUserResponseDto convertDto(Company company){
        return CompanyUserResponseDto.builder()
                .email(company.getManagerEmail())
                .cName(company.getCompanyName())
                .managerName(company.getManagerName())
                .companyPhone(company.getCompanyPhonenumber())
                .category(company.getCompanyCategory())
                .license(company.getCompanyLicense())
                .address(company.getCompanyAddress())
                .welfare(company.getCompanyWelfare())
                .vision(company.getCompanyVision())
                .history(company.getCompanyHistory())
                .ceoName(company.getMasterName())
                .managerPhone(company.getManagerPhonenumber())
                .cSize(company.getCompanySize())
                .logo(company.getCompanyLogo())
                .build();
    }
}
