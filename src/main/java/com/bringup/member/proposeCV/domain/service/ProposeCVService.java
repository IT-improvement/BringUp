package com.bringup.member.proposeCV.domain.service;

import com.bringup.member.proposeCV.domain.ProposeCVEntity;
import com.bringup.member.proposeCV.repository.ProposeCVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProposeCVService {
    private final ProposeCVRepository proposeCVRepository;

    public List<ProposeCVEntity> findAll(){
        return proposeCVRepository.findAll();
    }
}
