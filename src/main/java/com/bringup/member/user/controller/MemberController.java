package com.bringup.member.user.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.domain.service.JoinService;
import com.bringup.member.user.domain.service.MemberService;
import com.bringup.member.user.domain.service.UserLoginService;
import com.bringup.member.user.dto.JoinDTO;
import com.bringup.member.user.dto.MemberUpdateDto;
import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.security.Principal;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final JoinService joinService;
    private final UserLoginService userLoginService;
    private final UserRepository userRepository;
    private final MemberService memberService;

    @PostMapping("/name")
    public ResponseEntity<BfResponse<?>> getMemberName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userName = memberService.getUserName(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, userName));
    }

    @PutMapping("/mem")
    public ResponseEntity<BfResponse<?>> updateMember(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Map<String, String> requestBody){
        memberService.updateMember(userDetails, requestBody);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Member update successful")));
    }

    @DeleteMapping("/mem")
    public ResponseEntity<BfResponse<?>> deleteMember(@AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.deleteMember(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Member update successful")));
    }

    @GetMapping("/memberInfo/post")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserEntity user = memberService.getMemberInfo(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, user));
    }



}
