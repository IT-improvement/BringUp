/*
package com.bringup.member.resume.dto.response;

import com.bringup.member.resume.domain.entity.CVEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CVReadResponseDto {
    private String cvImage;
    private boolean mainCv;
    private String education;
    private String skill;
    private int userIndex;

    public CVReadResponseDto(CVEntity cv){
        this.mainCv = cv.isMainCv();
        this.skill = cv.getSkill();
        this.userIndex = cv.getUserIndex();
    }
}
*/
