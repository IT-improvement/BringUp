package com.bringup.company.recruitment.dto.response;

import com.bringup.common.enums.RecruitmentType;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnifiedRecruitmentDto {
    private Integer index;               // 공고 인덱스
    private String title;             // 공고 제목
    private ApplicationType type;              // 공고 타입 (프리랜서 or 일반)
    private RecruitmentType recruitmentType; // 정규직, 비정규직, 아르바이트 중 하나
    private String period;            // 채용 종료일
    private int viewCount;            // 조회수
    private int applicantCount;       // 지원자 수
}