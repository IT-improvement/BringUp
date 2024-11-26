package com.bringup.member.recruitment.dto.response;

import com.bringup.member.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentVisitResponseDto {
    private int visitIndex;
    private UserEntity user;
    private Integer recruitmentIndex;
    private LocalDateTime visitDate;
    private String recruitmentTitle;
    private String companyName;
}
