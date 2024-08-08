package com.bringup.member.notice.domain.service;

import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import com.bringup.member.notice.domain.repository.UserRecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecruitmentService {

    private final UserRecruitmentRepository userRecruitmentRepository;

    public List<UserRecruitmentEntity> getAllRecruitments(){
        return userRecruitmentRepository.findAll();
    }
}