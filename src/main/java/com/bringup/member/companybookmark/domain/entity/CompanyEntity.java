package com.bringup.member.companybookmark.domain.entity;

import com.bringup.member.companybookmark.dto.request.CompanyRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String managerEmail;
    private String companyName;
    private String companyCategory;
    private String companyPhoneNumber;
    private String companyAdress;
    private String companyLogo;
    private int companySize;

    public CompanyEntity(CompanyRequestDto dto){
        this.managerEmail = dto.getManagerEmail();
        this.companyName = dto.getCompanyName();
        this.companyCategory = dto.getCompanyCategory();
        this.companyPhoneNumber = dto.getCompanyPhoneNumber();
        this.companyAdress = dto.getCompanyAdress();
        this.companyLogo = dto.getCompanyLogo();
        this.companySize = dto.getCompanySize();
    }
}
