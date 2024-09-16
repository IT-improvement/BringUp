package com.bringup.member.applyRecruitment.domain.service;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.repository.ApplyRecruitmentRepository;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyRecruitmentService {
    private final ApplyRecruitmentRepository applyRecruitmentRepository;
    private final UserRepository userRepository;
    private final CVRepository cvRepository;

    public ApplyRecruitmentEntity getApplyRecruitment(UserDetailsImpl userDetails){
        //applyRecruitmentRepository.findByCvIndexAndRecruitmentIndex(userDetails.);
        return null;
    }

    
}
