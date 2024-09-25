package com.bringup.common.bookmark.domain.service.implement;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.bookmark.domain.repository.CompanyBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyBookMarkServiceImpl {
    private final CompanyBookMarkRepository companyBookMarkRepository;

    public List<CompanyBookMarkEntity> findAll(){
        return companyBookMarkRepository.findAll();
    }
}
