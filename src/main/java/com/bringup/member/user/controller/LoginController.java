package com.bringup.member.user.controller;

import com.bringup.common.response.BfResponse;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.service.UserLoginService;
import com.bringup.member.user.dto.UserLoginDTO;
import com.bringup.member.user.dto.UserLoginTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;



}
