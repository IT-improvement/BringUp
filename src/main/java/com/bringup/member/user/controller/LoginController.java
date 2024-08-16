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

    /* 클라이언트의 로그인 요청
    클라이언트가 사용자의 이메일(userEmail)과 비밀번호(userPassword)를 POST 요청으로 /member/userLogin 엔드포인트에 보냅니다.
     */
    @PostMapping("/userLogin")
    public ResponseEntity<BfResponse<UserLoginTokenDTO>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        // 디버그 로그 추가
        System.out.println("Controller: login method called with " + loginDTO.getUserEmail());
        return ResponseEntity.ok(new BfResponse<>(userLoginService.login(loginDTO)));
        /*  로직 설명
        1.LoginController에서 로그인 요청 처리
    LoginController의 login 메서드가 요청을 받습니다.
    login 메서드는 전달된 UserLoginDTO 객체(로그인 정보가 포함된 DTO)를 사용하여 UserLoginService의 login 메서드를 호출합니다.
    LoginController는 UserLoginService로부터 반환된 UserLoginTokenDTO를 클라이언트에 응답합니다.
     */
    }
    // Email 중복 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<BfResponse<?>> checkEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        boolean isAvailable = userLoginService.checkEmail(userEmail);
        return ResponseEntity.ok(new BfResponse<>(isAvailable));
    }

}
