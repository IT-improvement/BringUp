package com.bringup.company.headhunt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeadhuntResponseDto {
    private int cvIndex;
    //private String cvImage;
    private boolean mainCv;
    //private String education;
    private String skill;
    private String userAddress;
    private int userIndex;
    private String userName;
}