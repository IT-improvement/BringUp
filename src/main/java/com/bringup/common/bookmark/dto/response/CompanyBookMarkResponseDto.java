package com.bringup.common.bookmark.dto.response;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.enums.BookmarkType;
import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyBookMarkResponseDto{
    private UserEntity user;
    private Company company;
    private BookmarkType status;

    public CompanyBookMarkResponseDto(CompanyBookMarkEntity companyBookMarkEntity){
        this.user = companyBookMarkEntity.getUser();
        this.company = companyBookMarkEntity.getCompany();
        this.status = companyBookMarkEntity.getStatus();
    }
}
