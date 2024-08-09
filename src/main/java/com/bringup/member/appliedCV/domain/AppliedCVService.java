package com.bringup.member.appliedCV.domain;

import com.bringup.member.appliedCV.repository.AppliedCVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppliedCVService {
    private final AppliedCVRepository appliedCVRepository;

    public List<AppliedCVEntity> findAll(){
        return appliedCVRepository.findAll();
    }
}
