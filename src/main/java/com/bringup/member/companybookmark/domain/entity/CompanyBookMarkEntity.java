package com.bringup.member.companybookmark.domain.entity;

import com.bringup.member.companybookmark.dto.request.CompanyBookMarkRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "company_bookmark")
public class CompanyBookMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyIndex;

    private int userIndex;
    private String status;
}
