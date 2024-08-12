package com.bringup.admin.user.Controller;

import com.bringup.admin.user.DTO.request.AdminCreationRequest;
import com.bringup.admin.user.DTO.request.AdminLoginDto;
import com.bringup.admin.user.DTO.response.AdminLoginTokenDto;
import com.bringup.admin.user.Service.AdminService;
import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.response.BfResponse;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<BfResponse<UserEntity>> createAdminAccount(@RequestBody AdminCreationRequest adminCreationRequest) {
        UserEntity createdAdmin = adminService.createAdminAccount(adminCreationRequest);
        BfResponse<UserEntity> response = new BfResponse<>(GlobalSuccessCode.SUCCESS, createdAdmin);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BfResponse<AdminLoginTokenDto>> login(@RequestBody AdminLoginDto loginRequestDto) {
        AdminLoginTokenDto tokenDto = adminService.login(loginRequestDto);
        BfResponse<AdminLoginTokenDto> response = new BfResponse<>(GlobalSuccessCode.SUCCESS, tokenDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}