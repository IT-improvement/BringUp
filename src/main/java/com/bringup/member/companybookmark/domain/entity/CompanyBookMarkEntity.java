package com.bringup.member.companybookmark.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "company_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CompanyBookMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyIndex;
    private int userIndex;
    private String status;
}
