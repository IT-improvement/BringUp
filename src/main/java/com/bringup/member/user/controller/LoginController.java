package com.bringup.member.user.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.company.user.DTO.request.LoginDto;
import com.bringup.company.user.DTO.response.LoginTokenDto;
import com.bringup.member.user.domain.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {



    @GetMapping("/userLoginForm")
    public String loginP(){
        return "member/user/login";
    }

    @PostMapping("/userLogin")
    public ResponseEntity<BfResponse<LoginTokenDto>> login(@Valid @RequestBody LoginDto loginDto) {
        // 디버그 로그 추가
        System.out.println("Controller: login method called with " + loginDto.getUserEmail());
        return ResponseEntity.ok(new BfResponse<>(LoginService.login(loginDto)));
    }

    // Email 중복 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<BfResponse<?>> checkEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        boolean isAvailable = LoginService.checkEmail(userEmail);
        return ResponseEntity.ok(new BfResponse<>(isAvailable));
    }

}
