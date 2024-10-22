package com.bringup.member.portfolio.blog.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.blog.domain.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/list")
    public ResponseEntity<?> blogList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userIndex = userDetails.getId();
        return blogService.getBLogList(userIndex);
    }
}
