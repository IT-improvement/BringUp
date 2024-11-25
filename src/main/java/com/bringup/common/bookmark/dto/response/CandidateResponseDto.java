package com.bringup.common.bookmark.dto.response;

import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponseDto {
    private int cvIndex;
    private String cvImage;
    private String userName;
    private String userEmail;
    private String userAddress;

    public CandidateResponseDto(CVEntity cv, UserEntity user) {
        this.cvIndex = cv.getCvIndex();

        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userAddress = user.getUserAddress();
    }
}
