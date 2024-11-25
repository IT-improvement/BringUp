package com.bringup.member.resume.domain.service;

import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.request.CVPortfolioRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import com.bringup.member.resume.dto.response.CVReadResponseDto;
import org.springframework.http.ResponseEntity;

public interface CVService {

    ResponseEntity<? super CVInsertResponseDto> insertCv(CVInsertRequestDto request);
}
