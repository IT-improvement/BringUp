package com.bringup.company.member.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    /*
    private String userid;
    private String username;
    private String password;
    private String email;
     */

    private String company_opendate; // 개업일자
    private String company_licence; // 사업자 등록 번호
    private String master_name; // CEO 이름
    // ㅡㅡㅡㅡㅡㅡㅡ1차 회원가입시 필요함ㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    private String id; // entity에서 이메일
    private String password;
    private String c_phone;
    private String c_name; // 회사명
    private String m_name; // 담당자명
    private String m_phone; // 담당자 번호
    private String c_address; // 회사 주소
    private String c_category; // 업종
    private String c_content; // 사업내용
    private String welfare_benefits; // 복지
    private String c_history; // 연혁
    private String c_scale; // 회사 규모(중소/중견/대/공)
    private String c_vision; // 회사 비전
    private String c_logo; // 회사 로고
    private int c_size; // 직원 수
    private String c_homePage; // 회사 홈페이지
    private String subsidiary; // 계열사
    private String financial_stat; // 재무재표
    private int cv_key; // 이력서 열람 키 갯수

    // 직급별 연봉 정보 리스트
    private List<SalaryDto> salaries;
}
