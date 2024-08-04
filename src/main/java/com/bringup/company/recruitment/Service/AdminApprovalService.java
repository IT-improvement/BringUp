package com.bringup.company.recruitment.Service;

import com.bringup.company.recruitment.Entity.Recruitment;
import com.bringup.company.recruitment.Repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminApprovalService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void approveRecruitment(Integer recruitmentIndex) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        recruitment.setStatus("승인");
        recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void rejectRecruitment(Integer recruitmentIndex) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        recruitmentRepository.delete(recruitment);
    }
}