package com.bringup.member.resume.domain.service.implement;

import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
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
        cvRepository.save(cv);
        return CVInsertResponseDto.success();
    }
}
