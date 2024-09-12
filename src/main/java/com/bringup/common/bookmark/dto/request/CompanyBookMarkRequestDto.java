package com.bringup.common.bookmark.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyBookMarkRequestDto {
    private int userIndex;
    private int companyIndex;
    private String status;
}
