package com.bringup.member.membership.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.membership.domain.service.UserMembershipService;
import com.bringup.member.membership.dto.request.UserMembershipDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/membership")
@RequiredArgsConstructor
public class UserMembershipController {

    private final UserMembershipService userMembershipService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeMembership(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserMembershipDto membershipDto) {

        if (userDetails == null) {
            System.out.println("인증되지 않은 사용자입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        int userIndex = userDetails.getId();
        System.out.println("User Index: " + userIndex);

        membershipDto.setUserIndex(userIndex);

        UserMembershipDto savedMembership = userMembershipService.subscribeMembership(userIndex, membershipDto.getPeriod());
        System.out.println("Membership DTO: " + savedMembership);

        return ResponseEntity.ok(savedMembership);
    }
}
