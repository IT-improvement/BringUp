package com.bringup.member.companylist.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.member.companylist.domain.service.CompanyListService;
import com.bringup.member.companylist.dto.response.CompanyListResponseDto;
import com.bringup.member.companylist.exception.CompanyListException;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bringup.common.enums.GlobalErrorCode.*;
import static com.bringup.common.enums.GlobalSuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyListController {
    private final CompanyListService companyListService;
    private final ErrorResponseHandler errorResponseHandler;

    @GetMapping("/list")
    public ResponseEntity<BfResponse<?>> getAllCompany(){
        try {
            List<CompanyListResponseDto> companies = companyListService.getAllCompany();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, companies));
        }catch (CompanyListException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(INTERNAL_SERVER_ERROR);
        }
    }
}
