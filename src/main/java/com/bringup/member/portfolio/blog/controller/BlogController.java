package com.bringup.member.portfolio.blog.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.blog.domain.BlogService;
import com.bringup.member.portfolio.blog.dto.request.BlogInsertRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public ResponseEntity<?> insertBlog(@RequestBody BlogInsertRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userIndex = userDetails.getId();
        System.out.printf("inserrid" + userIndex);
        return blogService.insertBlog(blogRequestDto, userIndex);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlog(@RequestParam(name = "index") String index){
        int blogIndex = Integer.parseInt(index);
        return blogService.deleteBlog(blogIndex);
    }
}
