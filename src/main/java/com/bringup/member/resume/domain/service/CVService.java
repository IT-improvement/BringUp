package com.bringup.member.resume.domain.service;

import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import org.springframework.http.ResponseEntity;

public interface CVService {

    ResponseEntity<? super CVInsertResponseDto> insertCv(CVInsertRequestDto request);
}