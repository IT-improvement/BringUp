package com.bringup.common.bookmark.dto.response;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.enums.BookmarkType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyBookMarkResponseDto{
    private int userIndex;
    private int companyIndex;
    private BookmarkType status;

    public CompanyBookMarkResponseDto(CompanyBookMarkEntity companyBookMarkEntity){
        this.userIndex = companyBookMarkEntity.getUser().getUserIndex();
        this.companyIndex = companyBookMarkEntity.getCompany().getCompanyId();
        this.status = companyBookMarkEntity.getStatus();
    }
}
