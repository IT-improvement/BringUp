package com.bringup.member.user.controller;

import com.bringup.common.response.BfResponse;

import com.bringup.member.user.domain.service.UserLoginService;
import com.bringup.member.user.dto.UserLoginDTO;
import com.bringup.member.user.dto.UserLoginTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final UserLoginService userLoginService;

    @GetMapping("/userLoginForm")
    public String loginP(){
        return "member/user/login";
    }

    @PostMapping("/userLogin")
    public ResponseEntity<BfResponse<UserLoginTokenDTO>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        // 디버그 로그 추가
        System.out.println("Controller: login method called with " + loginDTO.getUserEmail());
        return ResponseEntity.ok(new BfResponse<>(userLoginService.login(loginDTO)));
    }

    // Email 중복 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<BfResponse<?>> checkEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        boolean isAvailable = userLoginService.checkEmail(userEmail);
        return ResponseEntity.ok(new BfResponse<>(isAvailable));
    }

}
