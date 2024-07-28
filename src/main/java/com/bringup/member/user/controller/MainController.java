package com.bringup.member.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main() {
        return "member/main"; // 실제 파일명은 main.jsp
    }
}
