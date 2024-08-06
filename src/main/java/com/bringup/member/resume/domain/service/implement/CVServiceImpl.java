package com.bringup.member.resume.domain.service.implement;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.request.CVPortfolioRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;

    @Override
    public ResponseEntity<? super CVInsertResponseDto> insertCv(CVInsertRequestDto request) {
        CVEntity cv = new CVEntity(request);
        try {
            cvRepository.save(cv);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CVInsertResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CVInsertResponseDto> insertPortfolio(CVPortfolioRequestDto request) {



        return null;
    }
}
