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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Member;
import java.security.Principal;

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


    //회원정보 변경 폼 "Get"
    @GetMapping(value = "/updateForm")
    public String updateMemberForm(Principal principal, Model model){
        String loginId = principal.getName();
        UserEntity memberId;
        //Optional 오류
        /*UserEntity memberId = userRepository.findByUserEmail(loginId);
        model.addAttribute("member", memberId);*/
        return "/settings/MemberUpdateForm";
    }

    @PostMapping(value = "/updateForm")
    public String updateMember(@Valid MemberUpdateDto memberUpdateDto, Model model){
        model.addAttribute("member", memberUpdateDto);
//        memberService.updateMember(memberUpdateDto);
        return "redirect:/member/myprofile";
    }
}
