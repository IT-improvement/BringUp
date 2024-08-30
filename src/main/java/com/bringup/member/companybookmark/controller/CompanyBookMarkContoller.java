package com.bringup.member.companybookmark.controller;

import com.bringup.member.companybookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.member.companybookmark.domain.service.CompanyBookMarkService;
import com.bringup.member.companybookmark.dto.request.CompanyBookMarkRequestDto;
import com.bringup.member.companybookmark.dto.response.CompanyBookMarkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyBookMarkContoller {
    @Autowired
    private CompanyBookMarkService companyBookMarkService;

    @PostMapping("/company_bookmark/add")
    public ResponseEntity<CompanyBookMarkResponseDto> addCompanyBookMark(@RequestBody CompanyBookMarkRequestDto companyBookMarkRequestDto){
        CompanyBookMarkResponseDto companyBookMarkResponseDto = companyBookMarkService.addCompanyBookMark(companyBookMarkRequestDto);
        return ResponseEntity.ok(companyBookMarkResponseDto);
    }

    @GetMapping("/company_bookmark/{userIndex}")
    public ResponseEntity<List<CompanyBookMarkResponseDto>> getCompanyBookMarks(@PathVariable int userIndex){
        List<CompanyBookMarkResponseDto> companyBookMarkEntityList = companyBookMarkService.getCompanyBookMarks(userIndex);
        return ResponseEntity.ok(companyBookMarkEntityList);
    }

    @PutMapping("/company_bookmark/delete")
    public ResponseEntity<Void> removeCompanyBookMark(@RequestBody CompanyBookMarkRequestDto companyBookMarkRequestDto){
        companyBookMarkService.removeCompanyBookMark(companyBookMarkRequestDto);
        return ResponseEntity.ok().build();
    }
}
