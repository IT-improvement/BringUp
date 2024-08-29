package com.bringup.member.companybookmark.domain.service;

import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.member.companybookmark.domain.repository.CompanyBookMarkRepository;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyBookMarkService {
    private final CompanyBookMarkRepository companyBookMarkRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final CVRepository cvRepository;
}
